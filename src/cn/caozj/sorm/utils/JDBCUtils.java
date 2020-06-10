package cn.caozj.sorm.utils;

import java.rmi.server.ExportException;
import java.sql.PreparedStatement;

public class JDBCUtils {

    public static void handleParams(PreparedStatement ps, Object[] params){
        if(params != null){
            for(int i = 0; i < params.length; i++){
                try {
                    ps.setObject(1+i, params[i]);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
