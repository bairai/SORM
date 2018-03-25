package com.sorm.core;

import java.util.List;

/**
 *
 * 负责对外提供服务的核心类
 * @author :fanghaoda
 */
public interface Query {
    /**
     * 直接执行一个DML语句
     * @param sql   sql语句
     * @param params  参数
     * @return    执行sql语句之后影响了记录的行数
     */
    public int executeDML(String sql,Object[] params);

    /**
     * 将一个对象存储到数据库中
     * 把对象中不为NULL的属性往数据库中存储 ，如果数字为null 则为零
     * @param obj 要存储的对象
     */
    public void insert(Object obj);

    /**
     *  1、删除clazz表示类对应的表中的记录（指定主键值id的记录）
     * @param clazz
     * @param object
     * @return
     */
    public void delete(Class clazz,Object object);      //delete from User where id=2

    /**
     * 删除对象在数据库中的记录（对象所在的类对应的表，对象的主键值对应到表）
     * @param obj
     */
    public void delete(Object obj);

    /**
     * 更新对象对应的记录，并且只更新指定的字段的值
     * @param obj 所要更新的对象
     * @param fieldNames 更新的属性列表
     * @return 影响了几行数据
     */
    public int update(Object obj,String[] fieldNames);// update user set uname=?,pwd=?;

    /**
     * 查询 返回 多行记录，并将每行记录封装到clazz指定的类的对象中
     * @param sql 查询语句
     * @param clazz  封装数据的javabean类的class对象
     * @param params sql的参数
     * @return 查询的结果
     */
    public List queryRows(String sql, Class clazz, Object[] params);

    /**
     * 查询 返回一行记录，并将每行记录封装到clazz指定的类的对象中
     * @param sql 查询语句
     * @param clazz  封装数据的javabean类的class对象
     * @param params sql的参数
     * @return 查询的结果
     */
    public Object queryUniqueRow(String sql, Class clazz, Object[] params);
    /**
     * 查询 返回一个值（），并将值返回
     * @param sql 查询语句
     * @param params sql的参数
     * @return 查询的结果
     */
    public Object queryValue(String sql, Object[] params);

    /**
     * 查询 返回一个数字，并将值返回
     * @param sql 查询语句
     * @param params sql的参数
     * @return 查询的结果
     */
    public Number queryNumber(String sql, Object[] params);


}
