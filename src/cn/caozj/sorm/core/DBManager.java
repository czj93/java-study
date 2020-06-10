package cn.caozj.sorm.core;

import cn.caozj.sorm.bean.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 根据配置信息, 维持连接的管理
 */
public class DBManager {

    private static Configuration conf;
    static {
        Properties pros = new Properties();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        }catch (Exception e){
            e.printStackTrace();
        }

        conf = new Configuration();

        conf.setDriver(pros.getProperty("driver"));
        conf.setPoPackage(pros.getProperty("poPackage"));
        conf.setPwd(pros.getProperty("pwd"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setUrl(pros.getProperty("url"));
        conf.setUser(pros.getProperty("user"));
        conf.setUsingDB(pros.getProperty("usingDB"));

    }


    public static Connection getConn(){
        try{
            Class.forName(conf.getDriver());
            return DriverManager.getConnection(conf.getUrl(), conf.getUser(), conf.getPwd());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Configuration getConf() {
        return conf;
    }

    public static void close(Connection conn, PreparedStatement ps){
        if(conn != null){
            try {
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        if(ps != null){
            try {
                ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
