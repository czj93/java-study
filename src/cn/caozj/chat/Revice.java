package cn.caozj.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

// 接收消息
public class Revice implements Runnable{
    Socket client;
    Boolean isRunning = true;
    DataInputStream dis;
    public Revice(Socket client){
        this.client = client;

        try {
            dis = new DataInputStream(client.getInputStream());
        }catch (IOException e){
//            e.printStackTrace();
            release();
        }
    }

    @Override
    public void run() {
        while (isRunning){
            String msg = "";
            try {
                msg = dis.readUTF();
                System.out.println(msg);
                if(msg.equals("exit")){
                    isRunning = false;
                }
            }catch (IOException e){
//                e.printStackTrace();
                release();
            }
        }
    }

    public  void release(){
        Utils.close(dis);
    }
}
