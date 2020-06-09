package cn.caozj.sorm.core;

public class MysqlTypeConvertor implements TypeConvertor {

    @Override
    public String databaseType2JavaType(String columnType) {

        if("varchar".equalsIgnoreCase(columnType) || "char".equalsIgnoreCase(columnType)){
            return "String";
        }else if("int".equalsIgnoreCase(columnType) ||
                "tinyint".equalsIgnoreCase(columnType) ||
                "smallint".equalsIgnoreCase(columnType) ||
                "integer".equalsIgnoreCase(columnType)
        ){
            return "Integer";
        }else if("bigint".equalsIgnoreCase(columnType)){
            return "Long";
        }else if("double".equalsIgnoreCase(columnType) || "float".equalsIgnoreCase(columnType)){
            return "Double";
        }else if("time".equalsIgnoreCase(columnType)){
            return "java.sql.Time";
        }
        return null;
    }

    @Override
    public String javaType2DatabaseType(String columnType) {
        return null;
    }
}
