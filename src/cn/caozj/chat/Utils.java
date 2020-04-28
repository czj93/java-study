package cn.caozj.chat;

import java.io.Closeable;

public class Utils {

    public static void close(Closeable... targets){
        for (Closeable target : targets){
            try{
                if(target != null){
                    target.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
