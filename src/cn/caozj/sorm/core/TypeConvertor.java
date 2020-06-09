package cn.caozj.sorm.core;

/**
 * 复制 java 数据类型 和 数据库类型 互相转化
 */
public interface TypeConvertor {

    /**
     * 将数据库类型转化成 java 类型
     * @param columnType
     * @return
     */
    public String databaseType2JavaType(String columnType);

    /**
     * 将java 类型 转化成 数据库类型
     * @param columnType
     * @return
     */
    public String javaType2DatabaseType(String columnType);
}
