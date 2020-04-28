package cn.caozj.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Channel implements Runnable {
    String name = null;
    Socket client = null;
    Boolean isRunning = false;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    CopyOnWriteArrayList<Channel> allClient = null;

    public Channel(Socket client, CopyOnWriteArrayList<Channel> all){
        this.client = client;
        this.allClient = all;

        try {
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            name = revice();
            isRunning = true;
        }catch (IOException e){
//            e.printStackTrace();
            release();
        }
    }

    public String revice(){
        String msg = "";
        try {
            msg = dis.readUTF();
        }catch (IOException e){
//            e.printStackTrace();
            release();
        }
        return msg;
    }

    public void send(String msg){
        try {
            dos.writeUTF(msg);
            dos.flush();
        }catch (IOException e){
//            e.printStackTrace();
            release();
        }

    }

    public void sendOthers(String msg){
        if(this.allClient.size() > 0){
            this.allClient.forEach(channel -> {
                if(channel != this){
                    channel.send( name + ":" + msg);
                }
            });
        }
    }

    public void release(){
        sendOthers(this.name + "退出");
        this.allClient.remove(this);
        Utils.close(dos, dis, client);
    }


    public void run(){
        while (isRunning){
            String msg = revice();

            if(!msg.equals("")){
//                send(msg);
                sendOthers(msg);
            }
            if(msg.equals("exit")){
                isRunning = false;
            }
        }
    }
}
