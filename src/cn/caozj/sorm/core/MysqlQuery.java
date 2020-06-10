package cn.caozj.sorm.core;

import cn.caozj.sorm.bean.ColumnInfo;
import cn.caozj.sorm.bean.TableInfo;
import cn.caozj.sorm.utils.JDBCUtils;
import cn.caozj.sorm.utils.ReflectUtils;
import cn.caozj.sorm.utils.StringUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class MysqlQuery implements Query {
    @Override
    public int executeDML(String sql, Object[] params) {

        Connection conn = DBManager.getConn();
        int count = 0;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            // 给 sql 传递参数
            JDBCUtils.handleParams(ps, params);

            count = ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBManager.close(conn, ps);
        }

        return count;
    }

    @Override
    public void insert(Object obj) {

    }

    @Override
    public int delete(Class clz, Object id) {
        TableInfo tableInfo = TableContext.poClassTableMap.get(clz);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        // 拼sql
        String sql = "delete from " + tableInfo.getTname() + " where  " + onlyPriKey.getName() + "=?";

        executeDML(sql, new Object[]{id});
        return 0;
    }

    @Override
    public void delete(Object obj) {
        Class clz = obj.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(clz);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        delete(clz, ReflectUtils.invokeGet(clz, obj, onlyPriKey.getName()));
    }

    @Override
    public int update(Object obj, String[] fieldNames) {
        return 0;
    }

    @Override
    public List queryRows(String sql, Class clz, Object[] params) {
        return null;
    }

    @Override
    public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
        return null;
    }

    @Override
    public Object queryValue(String sql, Object[] params) {
        return null;
    }
}
