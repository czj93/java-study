package cn.caozj.sorm.pool;

import cn.caozj.sorm.core.DBManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库连接池
 */
public class DBConnPool {
    private List<Connection> pool = null;
    /**
     * 最大连接数
     */
    private static final int POOL_MAX_SIZE = 20;

    /**
     * 初始化最小连接数
     */
    private static final int POOL_MIN_SIZE = 10;

    public DBConnPool(){
        initPool();
    }

    public void initPool(){
        if(pool == null){
            pool = new ArrayList<>();
        }
        while (pool.size() < POOL_MIN_SIZE){
            pool.add(DBManager.createConn());
        }
    }

    public synchronized Connection getConnection(){
        if(pool.size() > 0){
            int lastIndex = pool.size() - 1;
            Connection conn = pool.get(lastIndex);
            pool.remove(lastIndex);
            return conn;
        }else{
            return DBManager.createConn();
        }
    }

    public synchronized void closeConnection(Connection conn){
        if(pool.size() > POOL_MAX_SIZE){
            System.out.println("close");
            if(conn != null){
                try{
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("add close");
            pool.add(conn);
        }
    }
}
