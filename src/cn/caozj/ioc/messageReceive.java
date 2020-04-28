package cn.caozj.ioc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class messageReceive implements Runnable {
    private String sender;
    private int receivePort;
    private DatagramSocket receiveSocket;

    public messageReceive(int receivePort, String sender){
        this.receivePort = receivePort;
        this.sender = sender;
        try {
            receiveSocket = new DatagramSocket(this.receivePort);
        }catch (SocketException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true){
            byte[] container = new byte[1024*10];
            DatagramPacket packet = new DatagramPacket(container, 0 , container.length);
            try{
                receiveSocket.receive(packet);
                byte[] datas = packet.getData();
                String msg = new String(datas, 0, datas.length);
                if(msg.equals("exit")){
                    break;
                }
                System.out.println(this.sender +":"+ msg);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        receiveSocket.close();
    }
}
