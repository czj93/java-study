package cn.caozj.sorm.core;

import cn.caozj.sorm.bean.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
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
}
