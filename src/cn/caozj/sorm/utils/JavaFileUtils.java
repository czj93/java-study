package cn.caozj.sorm.utils;

import cn.caozj.sorm.bean.ColumnInfo;
import cn.caozj.sorm.bean.JavaFieldGetSet;
import cn.caozj.sorm.core.TypeConvertor;

public class JavaFileUtils {

    public JavaFieldGetSet createFieldGetSet(ColumnInfo column, TypeConvertor convertor){
        String javaFieldType = convertor.databaseType2JavaType(column.getDataType());
        JavaFieldGetSet jfgs = new JavaFieldGetSet();
        jfgs.setFieldInfo("\tprivate " + javaFieldType + " " + column.getName() + ";\n");

        StringBuilder getInfo = new StringBuilder();
        getInfo.append("\tpublic void get");
        getInfo.append(StringUtils.firstChar2UpperCase(column.getName()) + "(){\n");
        getInfo.append("\treturn " + column.getName() + ";\n");
        getInfo.append("\t}\n");
        jfgs.setGetInfo(getInfo.toString());

        StringBuilder setInfo = new StringBuilder();
        setInfo.append("\tpublic void set" + StringUtils.firstChar2UpperCase(column.getName()) + "(");
        setInfo.append(javaFieldType + " " + column.getName() + "){\n");
        setInfo.append("\tthis."+ column.getName() + "=" + column.getName() + ";\n");
        setInfo.append("\t}\n");

        jfgs.setSetInfo(setInfo.toString());

        return jfgs;
    }
}
