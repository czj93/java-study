package cn.caozj.ioc;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

// 1. 使用 ServerSocket 监听一个服务
// 2. accpet 获取一个请求
// 3. socket.getInputStream 获取 输入流

public class tcpTest {
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(8082);

        Socket socket = server.accept();
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        System.out.println("一个链接建立");
        System.out.println(dis.readUTF());


        server.close();
    }
}
