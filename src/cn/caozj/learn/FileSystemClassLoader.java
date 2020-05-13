package cn.caozj.learn;

import java.io.*;
import java.lang.reflect.Constructor;
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

        // 运行前先创建一个 class 文件
        // 新建一个 HelloWorld 的 java 文件

        // HelloWorld.java
        // package cn.caozj;
        //
        // public class HelloWorld {
        //    public void sayHello(){
        //        System.out.println("hello");
        //    }
        //  }

        // 编译 java 代码
        // javac -d . HelloWorld.java
        // 控制台没有输出，则表示编译成功

        FileSystemClassLoader fileSystemClassLoader = new FileSystemClassLoader("e:/Code/lib/java");

        // 加载 class
        Class<?> c = fileSystemClassLoader.findClass("cn.caozj.HelloWorld");

        // 获取构造函数
        Constructor constructor = c.getDeclaredConstructor();

        // new 一个实例， 不同包下的 class, 必须是 public 修饰， 否者实例的化的时候 会报 IllegalAccessException 异常
        Object hello = constructor.newInstance();

        // 获取 sayHello 方法
        Method sayHello = c.getDeclaredMethod("sayHello");
//        sayHello.setAccessible(true);   // 禁止安全检查

        // 调用 sayHello 方法
        sayHello.invoke(hello);
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
//                    e.printStackTrace();
                }

                if(c != null){
                    return c;
                }else{
                    // 根据报名获取 class 字节数据
                    byte[] classData = getClassData(name);

                    // 定义 class
                    c = defineClass(name, classData, 0, classData.length);
                }
            }

        }

        return c;
    }

    private byte[] getClassData(String name){
        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String classPath = root + "/" + name.replace(".", "/")+".class";

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
