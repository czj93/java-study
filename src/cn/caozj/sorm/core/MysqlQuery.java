package cn.caozj.sorm.core;

import cn.caozj.sorm.bean.ColumnInfo;
import cn.caozj.sorm.bean.TableInfo;
import cn.caozj.sorm.utils.JDBCUtils;
import cn.caozj.sorm.utils.ReflectUtils;
import cn.caozj.sorm.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
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
        Class clz = obj.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(clz);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        // sql insert 格式 insert into 表名(studentID,name) values (?,?);
        StringBuilder sql = new StringBuilder();
        List<Object> sqlValues = new ArrayList<>();

        Field[] fields = clz.getDeclaredFields();
        int countNotNullFiled = 0;

        sql.append("insert into " + tableInfo.getTname());
        sql.append("(" );

        for(Field field : fields){
            String fieldName = field.getName();
            Object filedValue = ReflectUtils.invokeGet(clz, obj, fieldName);
            // 排除空值 和 主键
            if(filedValue != null && !fieldName.equals(onlyPriKey.getName())){
                sqlValues.add(filedValue);
                sql.append( fieldName + ",");
                countNotNullFiled++;
            }
        }

        sql.setCharAt(sql.length()-1, ')');
        sql.append(" values (" );

        for (int i = 0 ;  i < countNotNullFiled; i++){
            sql.append("?,");
        }

        sql.setCharAt(sql.length()-1, ')');

        executeDML(sql.toString(), sqlValues.toArray());
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
        // obj => update 表名 set name=?,studentID=? where id=?;

        Class clz = obj.getClass();
        List<Object> params = new ArrayList<>();
        TableInfo tableInfo = TableContext.poClassTableMap.get(clz);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        StringBuilder sql = new StringBuilder("update " + tableInfo.getTname() + " set ");
        for(String filed : fieldNames){

            sql.append(filed + "=?,");
            params.add(ReflectUtils.invokeGet(clz, obj, filed));
        }
        sql.setCharAt(sql.length()-1,  ' ');
        sql.append("where id=?" );
        params.add(ReflectUtils.invokeGet(clz, obj, onlyPriKey.getName()));

        return executeDML(sql.toString(), params.toArray());
    }

    @Override
    public List queryRows(String sql, Class clz, Object[] params) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object> list = null;
        try {
            ps = conn.prepareStatement(sql);
            JDBCUtils.handleParams(ps, params);
            rs = ps.executeQuery();                     // 执行查询条件
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()){
                if(list == null){
                    list = new ArrayList<>();
                }
                Object rowObj = clz.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++){
                    String columnName = metaData.getColumnLabel(i+1);
                    Object columnValue = rs.getObject(i+1);
                    ReflectUtils.invokeSet(rowObj, columnName, columnValue);
                }
                list.add(rowObj);
            }



            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBManager.close(conn, ps);
        }

        return list;
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
