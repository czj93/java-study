package cn.caozj.chat;

import java.io.*;
import java.net.Socket;

// 从控制台获取消息
// 发送消息
public class Send implements Runnable {
    Socket client;
    DataOutputStream dos = null;
    BufferedReader console = null;
    Boolean isRunning = true;
    String name = null;
    public Send(Socket client, String name){
        this.client = client;
        this.name = name;
        try {
            dos = new DataOutputStream(client.getOutputStream());
            console = new BufferedReader(new InputStreamReader(System.in));
            // 发送姓名
            dos.writeUTF(name);
            dos.flush();
        }catch (IOException e){
//            e.printStackTrace();
            release();
        }

    }

    @Override
    public void run() {
        while (isRunning){
            try{
                String msg = console.readLine();
                dos.writeUTF( msg);
                dos.flush();
                if(msg.equals("exit")){
                    isRunning = false;
                }
            }catch (IOException e){
//                e.printStackTrace();
                release();
            }
        }
    }

    public void release(){
        Utils.close(dos, console);
    }
}
