package cn.caozj.ioc;

import java.io.*;
import java.net.URL;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class ipAddr {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress add = InetAddress.getLocalHost();
        System.out.println("address:" + add.getAddress() );
        System.out.println("host name : " + add.getHostName());
        System.out.println("Host address:" + add.getHostAddress());

        // 下载 sowodika.com/index.html 并写入到 resouce/baidu.html 中
        try {
            URL url = new URL("http://www.sowodika.com");
            BufferedReader reader = null;
            FileWriter write = null;
            try {
                InputStream input = url.openStream();

                reader = new BufferedReader(new InputStreamReader(input));

                write = new FileWriter(new File("resource/baidu.html"));

                String str = null;

                while((str = reader.readLine()) != null){
                    write.append(str);
                }
                write.flush();

                if(write != null){
                    write.close();
                }

                if(reader != null){
                    reader.close();
                }

            }catch (IOException e){
                e.printStackTrace();
            }


        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
}
