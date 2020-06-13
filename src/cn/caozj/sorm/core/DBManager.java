package cn.caozj.sorm.core;

import cn.caozj.sorm.bean.Configuration;
import cn.caozj.sorm.pool.DBConnPool;

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
    private static DBConnPool pool;
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
//        conf.setMinPoolConnSize(Integer.getInteger(pros.getProperty("minPoolConnSize")));
//        conf.setMaxPoolConnSize(Integer.getInteger(pros.getProperty("maxPoolConnSize")));
        pool = new DBConnPool();
    }

    public static Connection createConn(){
        try{
            Class.forName(conf.getDriver());
            return DriverManager.getConnection(conf.getUrl(), conf.getUser(), conf.getPwd());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getConn(){
        try{
            return pool.getConnection();
//            Class.forName(conf.getDriver());
//            return DriverManager.getConnection(conf.getUrl(), conf.getUser(), conf.getPwd());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Configuration getConf() {
        return conf;
    }

    public static void close(Connection conn, PreparedStatement ps){

        pool.closeConnection(conn);
//        if(conn != null){
//            try {
//                conn.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }

        if(ps != null){
            try {
                ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
