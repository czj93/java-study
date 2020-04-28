package cn.caozj.ioc;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 1. 使用DatagramSocket 指定端口接收
 * 2. 使用DatagramPacket 创建数据包
 * 3. DatagramSocket 的 receive 接口数据
 * 4. 分析数据
 * 5. 释放资源
 */
public class udpClient {
    public static void main(String[] args) throws Exception{
//        sendText();       // 发送文字
//        sendFile();         // 发送文件
        new Thread(new messageClient(8888, new InetSocketAddress("localhost", 9999))).start();
        new Thread(new messageReceive(8889, "已")).start();
    }

    public static void sendFile() throws Exception {
        System.out.println("发送文件-----》");
        byte[] dats = IOUtils.fileToByte("resource/baidu.html");

        DatagramPacket packet = new DatagramPacket(dats, 0, dats.length, new InetSocketAddress("localhost", 9999));
        DatagramSocket client = new DatagramSocket(8888);

        client.send(packet);
        client.close();
        System.out.println("发送完毕");
    }


    public static void sendText() throws Exception{
        System.out.println("启动服务-----》" + System.currentTimeMillis());
        DatagramSocket client = new DatagramSocket(8888);
        String msg = "Hello world";
        byte[] datas = msg.getBytes();
        // 创建数据
        // DatagramPacket 接受字节数组
        // 使用 InetSocketAddress 创建数据包目的地址， IP地址 加端口

        DatagramPacket packet = new DatagramPacket(datas, 0, datas.length, new InetSocketAddress("localhost", 9999) );
        // 发送数据包
        System.out.println("发送时间：" + System.currentTimeMillis());
        client.send(packet);

        // 释放资源
        client.close();
    }
}
