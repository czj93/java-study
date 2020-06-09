package cn.caozj.sorm.core;

import cn.caozj.sorm.bean.ColumnInfo;
import cn.caozj.sorm.bean.TableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理数据的表结构 和 对应的类结构， 并可以根据表结构生成类结构
 */
public class TableContext {

    public static Map<String, TableInfo> tables = new HashMap<String, TableInfo>();

    public static Map<Class, TableInfo> poClassTableMap = new HashMap<Class, TableInfo>();

    private TableContext(){}

    static {
        try{
            Connection con = DBManager.getConn();
            DatabaseMetaData dbmd = con.getMetaData();  // 获取数据库的信息
            // 获取所有的表信息
            ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
            // 遍历所有表
            while(tableRet.next()){
                // 获取表名
                String tableName = (String) tableRet.getObject("TABLE_NAME");
                // new tableInfo
                TableInfo ti = new TableInfo(tableName, new ArrayList<ColumnInfo>(), new HashMap<String, ColumnInfo>());

                // 添加到 tables 中
                tables.put(tableName, ti);

                // 获取表中的所有字段
                ResultSet set = dbmd.getColumns(null, "%", tableName,"%");

                // 遍历保存所有字段
                while (set.next()){
                    ColumnInfo ci = new ColumnInfo(
                            set.getString("COLUMN_NAME"),
                            set.getString("TYPE_NAME"),
                            0
                    );
                    ti.getColums().put(set.getString("COLUMN_NAME"), ci);
                }

                // 获取所有的主键字段
                ResultSet priKeySet = dbmd.getPrimaryKeys(null, "%", tableName);
                // 遍历主键，将之前保存的字段设置成主键
                while(priKeySet.next()){
                    ColumnInfo ci2 = (ColumnInfo) ti.getColums().get(priKeySet.getString("COLUMN_NAME"));
                    ci2.setKeyType(1);
                    ti.getPriKeys().add(ci2);
                }

                if(ti.getPriKeys().size() > 0){
                    ti.setOnlyPriKey(ti.getColums().get(0));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static Map<String, TableInfo>getTableInfos(){
        return tables;
    }

    public static void main(String[] args){
        Map<String, TableInfo> tables = getTableInfos();
        System.out.println(tables);
    }
}
