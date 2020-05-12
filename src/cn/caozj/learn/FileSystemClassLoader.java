package cn.caozj.learn;

import java.io.*;
import java.lang.reflect.Method;

/**
 * 自定义文件系统class 加载
 * 输入类名， 加载指定目录下的 class 文件
 *
 */

public class FileSystemClassLoader extends ClassLoader {

    private String root;

    public static void main(String[] args) throws Exception{
        // 测试 FileSystemClassLoader

        FileSystemClassLoader fileSystemClassLoader = new FileSystemClassLoader("d:/code/java-learning/lesson-1");

        Class<?> c = fileSystemClassLoader.findClass("HelloWorld");

        c.getConstructor().newInstance();

        Method sayHello = c.getDeclaredMethod("sayHello");
        sayHello.invoke(c);
    }
    public FileSystemClassLoader(String root){
        this.root = root;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException{
        Class<?> c = findLoadedClass(name);
        // 判断 c 是否被加载过
        if(c != null){
            return c;
        }else{

            // 双亲委托模式
            // 先使用父类加载器加载

            ClassLoader pcl = this.getParent();
            if(pcl != null){
                try {
                    c = pcl.loadClass(name);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(c != null){
                    return c;
                }else{
                    // 根据报名获取 class 字节数据
                    byte[] classData = getClassData(name);

                    // 定义 class
                    defineClass(name, classData, 0, classData.length);
                }
            }

        }

        return c;
    }

    private byte[] getClassData(String name){
        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String classPath = root + "/" + name.replace(".", "/")+".class";
        System.out.println(classPath);

        try {
            is = new FileInputStream(classPath);

            byte[] car = new byte[1024];
            int len;

            while ((len = is.read(car)) != -1){
                bos.write(car, 0, len);
            }

            return bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            if(is != null){
                is.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

}
