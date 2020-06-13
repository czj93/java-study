package cn.caozj.sorm.core;

import cn.caozj.sorm.bean.ColumnInfo;
import cn.caozj.sorm.bean.TableInfo;
import cn.caozj.sorm.po.Student;
import cn.caozj.sorm.utils.JavaFileUtils;
import cn.caozj.sorm.utils.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                    ti.setOnlyPriKey(ti.getPriKeys().get(0));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        // 更新表结构
        updateTableJavaFile();

        loadPoTables();
    }


    public static Map<String, TableInfo>getTableInfos(){
        return tables;
    }

    public static void updateTableJavaFile(){
        TypeConvertor convertor = new MysqlTypeConvertor();
        for(TableInfo table: tables.values()){
            if(!table.getTname().equals("sys_config")){
                JavaFileUtils.createJavaBeanFile(table, convertor);
            }
        }
    }

    public static void loadPoTables(){
        for (TableInfo table : tables.values()){
            // 跳过 sys_config 这张表
            if(!table.getTname().equals("sys_config")){
                String cname = DBManager.getConf().getPoPackage() + "." + StringUtils.firstChar2UpperCase(table.getTname());
                try {
                    Class c = Class.forName(cname);
                    poClassTableMap.put(c, table);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        Map<String, TableInfo> tables = getTableInfos();
        System.out.println(tables);

        MysqlQuery query = new MysqlQuery();
        // 测试删除
//        Student st1 = new Student();
//        st1.setId(2);
//        query.delete(st1);

        //测试插入

//        Student st2 = new Student();
//        st2.setStudentID(2);
//        st2.setName("caozhijian");
//        query.insert(st2);

        // 测试 update
        Student st3 = new Student();
        st3.setId(4);
        st3.setName("caozhijian3");
        st3.setStudentID(662301);

        query.update(st3, new String[]{"name", "studentID"});

        // 测试查询
        List<Object> list = query.queryRows("select * from student where id=?", Student.class,  new Object[]{3});
        System.out.println(list);
    }
}
