package cn.caozj.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        Socket client = new Socket("localhost", 8888);

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String name = readName(console);
//        try {
//            console.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        new Thread(new Revice(client)).start();
        new Thread(new Send(client, name)).start();
    }

    public static String readName(BufferedReader console){
        String name = "";
        boolean read = true;
        while (read){
            System.out.println("请输入用户名");
            try {
                name = console.readLine();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(!name.equals("")){
                read = false;
            }
        }
        return name;
    }
}
