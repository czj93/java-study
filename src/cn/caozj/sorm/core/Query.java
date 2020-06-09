package cn.caozj.sorm.core;

import java.util.List;

/**
 * 提供查询
 */
public interface Query {

    /**
     * 执行一个DML语句
     * @param sql   sql语句
     * @param params    参数
     * @return  执行sql后影响的记录数
     */
    public int executeDML(String sql, Object params);

    public void insert(Object obj);

    public int delete(Class clz, int id);

    public void delete(Object obj);

    /**
     * 更新对象的记录， 并更新指定的记录
     * @param obj   需要更新的对象
     * @param fieldNames    需要更新的字段
     * @return  影响的记录数
     */
    public int update(Object obj, String[] fieldNames);

    /**
     * 查询返回多行记录， 并将每行记录封装到 clazz 的指定对象中
     * @param sql
     * @param clz 封装 记录的 javabean 的 class 对象
     * @param params sql 参数
     * @return
     */
    public List queryRows(String sql, Class clz, Object[] params);


    /**
     * 查询一行记录
     * @param sql
     * @param clazz
     * @param params
     * @return
     */
    public Object queryUniqueRow(String sql, Class clazz, Object[] params);

    /**
     * 查询一个值， 并将值返回
     * @param sql
     * @param params
     * @return
     */
    public Object queryValue(String sql, Object[] params);
}
