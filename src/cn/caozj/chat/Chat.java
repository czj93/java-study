package cn.caozj.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chat {
    static private CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(8888);

        while (true){
            Socket client = server.accept();
            Channel c = new Channel(client, all);
            all.add(c);
            new Thread(c).start();
        }
    }
}
