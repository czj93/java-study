package cn.caozj.server.servlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private int port = 8888;
    private ServerSocket serverSocket;
    private Boolean isRunning = false;


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }


    public void start(){
        try {
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

}
