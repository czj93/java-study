package cn.caozj.ioc;

import org.omg.CORBA.portable.InputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class messageClient implements Runnable {
    private int port;
    private BufferedReader reader;
    private DatagramSocket client;
    private InetSocketAddress address;
    public messageClient(int port, InetSocketAddress address){
        this.port = port;
        this.address = address;
        try {
            client = new DatagramSocket(this.port);
            reader = new BufferedReader(new InputStreamReader(System.in));
        }catch (SocketException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try{
                String msg = reader.readLine();
                byte[] datas = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(datas, 0, datas.length, this.address);
                client.send(packet);
                if(msg.equals("exit")){
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        client.close();
    }
}
