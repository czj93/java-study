package cn.caozj.ioc;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class udpServer {
    public static void main(String[] args) throws Exception{
//        revice();
//        reviceFile();

        new Thread(new messageClient(9998, new InetSocketAddress("localhost", 8889))).start();
        new Thread(new messageReceive(9999, "甲")).start();

    }

    public static void reviceFile() throws Exception{
        byte[] container = new byte[1024 * 1024];
        System.out.println("准备接收");
        DatagramPacket packet = new DatagramPacket(container, 0, container.length);
        DatagramSocket server = new DatagramSocket(9999);

        server.receive(packet);
        server.close();
        byte[] fileBytes = packet.getData();
        IOUtils.byteArrayToFile(fileBytes, "resource/Server/", "baidu.html");
    }

    public static void revice() throws Exception{
        byte[] container = new byte[1024];
        // 创建接收端
        DatagramSocket server = new DatagramSocket(9999);
        // 创建接收数据包
        DatagramPacket packet = new DatagramPacket(container, 0, container.length);
        // 接收
        System.out.println("接收数据, 时间：" + System.currentTimeMillis());
        server.receive(packet);

        // 获取包中数据
        byte[] datas = packet.getData();
        String msg = new String(datas, 0, datas.length);
        // 打印
        System.out.println( System.currentTimeMillis() + ": 收到消息---" + msg);
        server.close();
    }
}
