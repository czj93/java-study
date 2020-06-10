package cn.caozj.sorm.utils;

import cn.caozj.sorm.bean.ColumnInfo;
import cn.caozj.sorm.bean.JavaFieldGetSet;
import cn.caozj.sorm.bean.TableInfo;
import cn.caozj.sorm.core.DBManager;
import cn.caozj.sorm.core.TypeConvertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaFileUtils {

    public static JavaFieldGetSet createFieldGetSet(ColumnInfo column, TypeConvertor convertor){
        String javaFieldType = convertor.databaseType2JavaType(column.getDataType());
        JavaFieldGetSet jfgs = new JavaFieldGetSet();
        jfgs.setFieldInfo("\tprivate " + javaFieldType + " " + column.getName() + ";\n");

        StringBuilder getInfo = new StringBuilder();
        getInfo.append("\tpublic "+ javaFieldType +" get");
        getInfo.append(StringUtils.firstChar2UpperCase(column.getName()) + "(){\n");
        getInfo.append("\t\t\treturn this." + column.getName() + ";\n");
        getInfo.append("\t\t}\n");
        jfgs.setGetInfo(getInfo.toString());

        StringBuilder setInfo = new StringBuilder();
        setInfo.append("\tpublic void set" + StringUtils.firstChar2UpperCase(column.getName()) + "(");
        setInfo.append(javaFieldType + " " + column.getName() + "){\n");
        setInfo.append("\t\t\tthis."+ column.getName() + " = " + column.getName() + ";\n");
        setInfo.append("\t\t}\n");

        jfgs.setSetInfo(setInfo.toString());

        return jfgs;
    }

    /**
     * 根据 表信息 生成 javaBean 源码
     * @param tableInfo
     * @param convertor
     * @return avaBean 源码
     */
    public static String createJavaSrc(TableInfo tableInfo, TypeConvertor convertor){
        StringBuilder str = new StringBuilder();

         Map<String, ColumnInfo> columns = tableInfo.getColums();
         List<JavaFieldGetSet> javaFieldGetSetList = new ArrayList<>();

         for (ColumnInfo column : columns.values()){
             javaFieldGetSetList.add(createFieldGetSet(column, convertor));
         }

         str.append("package "+ DBManager.getConf().getPoPackage()+";\n\n");
         str.append("import java.sql.*;\n");
         str.append("import java.util.*;\n\n");

         StringBuilder filedsStr = new StringBuilder();
         StringBuilder getInfoStr = new StringBuilder();
         StringBuilder setInfoStr = new StringBuilder();
         for(JavaFieldGetSet jfgs: javaFieldGetSetList){
             filedsStr.append("\t" + jfgs.getFieldInfo() + "\n");
             getInfoStr.append("\t" + jfgs.getGetInfo() + "\n");
             setInfoStr.append("\t" + jfgs.getSetInfo() + "\n");
         }
         str.append("public class " + StringUtils.firstChar2UpperCase(tableInfo.getTname()) + "{\n");
         str.append(filedsStr);
         str.append(getInfoStr);
         str.append(setInfoStr);
         str.append("}\n");

        return str.toString();
    }

    public static void createJavaBeanFile(TableInfo table, TypeConvertor convertor){
        String src = createJavaSrc(table, convertor);
        BufferedWriter bw = null;
        String path = DBManager.getConf().getSrcPath();
        path += "\\";
        path += DBManager.getConf().getPoPackage().replaceAll("\\.", "\\\\");

        // 判断目录是否存在 则新建
        File srcDir = new File(path);
        if(!srcDir.exists()){
            srcDir.mkdirs();
        }

        String filePath = path + "\\" + StringUtils.firstChar2UpperCase(table.getTname()) + ".java";

        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(src);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bw != null){
                try {
                    bw.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
