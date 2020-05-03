package cn.caozj.server.servlet;

import java.io.*;
import java.lang.annotation.Annotation;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    private int port = 8888;
    private ServerSocket serverSocket;
    private Boolean isRunning = false;
    private String packageName = "cn.caozj.server";


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }


    public void start(){
        try {
            ArrayList<Class> classes = traversePackageClass();
            parseWebServletAnnotation(classes);
            serverSocket = new ServerSocket(port);
            isRunning = true;
            receive();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("ServerSocket exception");
        }
    }

    public void receive(){
        try {
            while (isRunning){
                Socket client = serverSocket.accept();
                new Thread(new Dispatcher(client)).start();
            }

        }catch (Exception e){
            stop();
            e.printStackTrace();
            System.out.println("客户端连接异常");
        }
    }

    public void stop(){
        isRunning = false;
        try {
            serverSocket.close();
            System.out.println("服务停止");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 遍历包下的所有 class 文件，解析 WebServlet 注解
    private ArrayList<Class> traversePackageClass(){
        ArrayList<Class> classes = new ArrayList<>();
        String packageDirPath = packageName.replace(".", "/");
        File packageDir = new File(packageDirPath);
        if(!packageDir.exists() || !packageDir.isDirectory()){
            return null;
        }


        File[] files = packageDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                // 只返回下的 文件， 没有递归目录查找文件
                return file.getName().endsWith(".class");
            }
        });

        for(File file: files){
            String fileName = file.getName();
            String className = fileName.substring(0, fileName.length() - 6);

            try {
                classes.add(Class.forName(packageName + "." + className));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return classes;
    }


    public void parseWebServletAnnotation(ArrayList<Class> classes){
        for(Class clz:classes){
            Annotation[] annotations = clz.getAnnotations();
            for (Annotation annotation: annotations){
                System.out.println(annotation);
            }
        }
    }


}
