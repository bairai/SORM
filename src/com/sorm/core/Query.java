package com.sorm.core;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.utils.JDBCUtils;
import com.sorm.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 负责对外提供服务的核心类
 * @author :fanghaoda
 */
public abstract class Query implements Cloneable{
    /**
     * 采用模版方法模式将JDBC操作封装成模版，便于重用
     * @param sql SQL语句
     * @param params SQL参数
     * @param clazz 记录要封装的java类
     * @param back 回调方法 匿名内部类
     * @return 返回查询结果
     */
    public Object excuteQueryTemplate(String sql,Object[] params,Class clazz,CallBack back){
        Connection connection=DBManager.getConn();
        List list=null;//存放查询结果的容器
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=connection.prepareStatement(sql);//给sql传参
            JDBCUtils.handleParams(ps,params);
            System.out.println(ps);
            rs=ps.executeQuery();
            return  back.doExecute(connection,ps,rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBManager.close(connection,ps);
        }
    }


    /**
     * 直接执行一个DML语句
     * @param sql   sql语句
     * @param params  参数
     * @return    执行sql语句之后影响了记录的行数
     */
    public int executeDML(String sql,Object[] params){
        Connection connection=DBManager.getConn();
        int count=0;
        PreparedStatement ps=null;
        try {
            ps=connection.prepareStatement(sql);
            JDBCUtils.handleParams(ps,params);
            System.out.println(ps);
            count= ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.close(connection,ps);
        }
        return count;
    }

    /**
     * 将一个对象存储到数据库中
     * 把对象中不为NULL的属性往数据库中存储 ，如果数字为null 则为零
     * @param obj 要存储的对象
     */
    public void insert(Object obj){//obj  --》 表中  INSERT INTO 表名（属性，属性，属性） VALUES(?,?,?);
        Class c=obj.getClass();
        List<Object> params=new ArrayList<Object>();//获得参数对象
        TableInfo tableInfo=TableContext.poClassTableMap.get(c);
        StringBuilder sql=new StringBuilder("INSERT INTO "+tableInfo.getTname()+" (");
        int CountNotNull=0;//计算不为空的属性值
        Field[] fs=c.getDeclaredFields();
        for(Field f:fs){
            String fieldName=f.getName();
            Object fieldValue=ReflectUtils.invokeGet(fieldName,obj);//利用反射调用obj的Get方法
            if(fieldValue!=null){
                CountNotNull++;
                sql.append(fieldName+",");
                params.add(fieldValue);
            }
        }
        sql.setCharAt(sql.length()-1,')');//将最后一个属性的，换成）
        sql.append(" VALUES (");
        for(int i=0;i<CountNotNull;i++){
            sql.append("?,");
        }
        sql.setCharAt(sql.length()-1,')');
        executeDML(sql.toString(),params.toArray());}

    /**
     *  1、删除clazz表示类对应的表中的记录（指定主键值id的记录）
     * @param clazz
     * @param object
     * @return
     */
    public void delete(Class clazz,Object object){
        // Emp.class,2 --> DELETE FROM emp WHERE id=2;
        //通过class对象找Tableinfo; User ---> User user
        //通过将Class对象和Tableinfo绑定在一起
        //delete from User where id=2
        TableInfo tableInfo= TableContext.poClassTableMap.get(clazz);
        //获取主键
        ColumnInfo onlyPriKey= tableInfo.getOnlyPriKey();
        String sql="DELETE FROM "+tableInfo.getTname()+" WHERE "+onlyPriKey.getName()+"= ?";
        executeDML(sql,new Object[]{object});
    }

    /**
     * 删除对象在数据库中的记录（对象所在的类对应的表，对象的主键值对应到表）
     * @param obj
     */
    public void delete(Object obj){
        Class c=obj.getClass();
        //  System.out.println(c.toString()); 引用了错误的包导致程序出现 NULLPoint的错误
        TableInfo tableInfo=TableContext.poClassTableMap.get(c);
        //  System.out.println(tableInfo);
        ColumnInfo onlyPriKey=tableInfo.getOnlyPriKey();
        //通过反射机制，调用属性对应的get方法或set方法
//        try {
//            Method m=c.getMethod("get"+StringUtils.firstChar2UpperCase(onlyPriKey.getName()),null);
//           Object priKeyValue= m.invoke(obj,null);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        Object priKeyValue= ReflectUtils.invokeGet(onlyPriKey.getName(),obj);
        delete(c,priKeyValue);
    }

    /**
     * 更新对象对应的记录，并且只更新指定的字段的值
     * @param obj 所要更新的对象
     * @param fieldNames 更新的属性列表
     * @return 影响了几行数据
     */
    public int update(Object obj,String[] fieldNames){  //obj{"uname","pwd"} --> UPDATE 表名 SET uname=?,pwd=? WHERE 主键=?;
        Class c=obj.getClass();
        List<Object> params=new ArrayList<Object>();
        TableInfo tableInfo=TableContext.poClassTableMap.get(c);
        StringBuilder sql=new StringBuilder("UPDATE "+tableInfo.getTname()+" SET ");
        ColumnInfo priKey=tableInfo.getOnlyPriKey();
        for(String fname:fieldNames){
            Object fvalue= ReflectUtils.invokeGet(fname,obj);
            sql.append(fname+"=?,");
            params.add(fvalue);
        }
        params.add(ReflectUtils.invokeGet(priKey.getName(),obj));//添加主键到list
        sql.setCharAt(sql.length()-1,' ');
        sql.append(" WHERE "+priKey.getName()+"=? ");//获取主键
        System.out.println(sql.toString());
        return executeDML(sql.toString(),params.toArray());}// update user set uname=?,pwd=?;

    /**
     * 查询 返回 多行记录，并将每行记录封装到clazz指定的类的对象中
     * @param sql 查询语句
     * @param clazz  封装数据的javabean类的class对象
     * @param params sql的参数
     * @return 查询的结果
     */
    public List queryRows(final String sql,final Class clazz,final Object[] params){
        return   (List) excuteQueryTemplate(sql, params, clazz, new CallBack() {
            @Override
            public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) {
                List list=null;
                try {
                    ps=conn.prepareStatement(sql);//给sql传参
                    JDBCUtils.handleParams(ps,params);
                    System.out.println(ps);
                    rs=ps.executeQuery();
                    ResultSetMetaData metaData = rs.getMetaData();
                    while(rs.next()){
                        if (list==null){
                            list=new ArrayList();
                        }
                        Object rowObj=clazz.newInstance();//调用javabean的无参构造器
                        //多列  SELECT 属性,属性,属性 FROM 表名 WHERE 主键=? and 属性>参数
                        for(int i=0;i<metaData.getColumnCount();i++){
                            String coulumnName=metaData.getColumnLabel(i+1);
                            Object columnValue=rs.getObject(i+1);
                            //调用rowOBJ对象的setUsername方法，将columnValue的值设置进去
//                    Method M=clazz.getDeclaredMethod("set"+StringUtils.firstChar2UpperCase(coulumnName),
//                            columnValue.getClass());
//                    M.invoke(rowObj,columnValue);
                            ReflectUtils.invokeSet(rowObj,coulumnName,columnValue);
                        }
                        list.add(rowObj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    DBManager.close(conn,ps);
                }
                return list;
            }
        });

      }

    /**
     * 查询 返回一行记录，并将每行记录封装到clazz指定的类的对象中
     * @param sql 查询语句
     * @param clazz  封装数据的javabean类的class对象
     * @param params sql的参数
     * @return 查询的结果
     */
    public Object queryUniqueRow(String sql, Class clazz, Object[] params){ List list=queryRows(sql,clazz,params);
        return (list==null||list.size()>0)?list.get(0):null;}
    /**
     * 查询 返回一个值（），并将值返回
     * @param sql 查询语句
     * @param params sql的参数
     * @return 查询的结果
     */
    public Object queryValue(String sql, Object[] params){
        return excuteQueryTemplate(sql, params, null, new CallBack() {

            @Override
            public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) {
                Object value=null;
                try {
                    while(rs.next()){
                        // SELECT COUNT(*) FORM USER
                        value=rs.getObject(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return value;
            }
        });
        }

    /**
     * 查询 返回一个数字，并将值返回
     * @param sql 查询语句
     * @param params sql的参数
     * @return 查询的结果
     */
    public Number queryNumber(String sql, Object[] params){return (Number) queryValue(sql,params);}

    /**
     * 分页查询
     * @param pageNum 第几页数据
     * @param size 每一页显示多少记录
     * @return
     */
    public abstract Object queryPagenate(int pageNum,int size);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
