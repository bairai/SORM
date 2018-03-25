package com.sorm.core;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.utils.JDBCUtils;
import com.sorm.utils.ReflectUtils;
import com.sorm.po.*;
import com.sorm.vo.EmpVO;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlQuery implements Query {
    @Override
    public int executeDML(String sql, Object[] params) {
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

    public void insert(Object obj) {
        //obj  --》 表中  INSERT INTO 表名（属性，属性，属性） VALUES(?,?,?);
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

        executeDML(sql.toString(),params.toArray());
    }

    @Override
    public void delete(Class clazz, Object obj) {
        // Emp.class,2 --> DELETE FROM emp WHERE id=2;
        //通过class对象找Tableinfo; User ---> User user
        //通过将Class对象和Tableinfo绑定在一起
        TableInfo tableInfo= TableContext.poClassTableMap.get(clazz);
        //获取主键
        ColumnInfo onlyPriKey= tableInfo.getOnlyPriKey();

        String sql="DELETE FROM "+tableInfo.getTname()+" WHERE "+onlyPriKey.getName()+"= ?";
        executeDML(sql,new Object[]{obj});
    }

    @Override
    public void delete(Object obj) {
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
        Object priKeyValue=ReflectUtils.invokeGet(onlyPriKey.getName(),obj);
        delete(c,priKeyValue);

    }

    @Override
    public int update(Object obj, String[] fieldNames) {
        //obj{"uname","pwd"} --> UPDATE 表名 SET uname=?,pwd=? WHERE 主键=?;
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


        return executeDML(sql.toString(),params.toArray());
    }

    @Override
    public List queryRows(String sql, Class clazz, Object[] params) {
        Connection connection=DBManager.getConn();
        List list=null;//存放查询结果的容器
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=connection.prepareStatement(sql);//给sql传参

            JDBCUtils.handleParams(ps,params);
            System.out.println(ps);
            rs=ps.executeQuery();
             ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                if(list==null){
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
            DBManager.close(connection,ps);
        }
        return list;
    }

    @Override
    public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
        List list=queryRows(sql,clazz,params);
        return (list==null&&list.size()>0)?null:list.get(0);
    }

    @Override
    public Object queryValue(String sql, Object[] params) {
        Connection connection=DBManager.getConn();
         Object value=null;//存放查询结果的对象
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=connection.prepareStatement(sql);//给sql传参
            JDBCUtils.handleParams(ps,params);
            System.out.println(ps);
            rs=ps.executeQuery();
            while(rs.next()){
                // SELECT COUNT(*) FORM USER
                value=rs.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(connection,ps);
        }
        return value;
    }

    @Override
    public Number queryNumber(String sql, Object[] params) {
        return (Number) queryValue(sql,params);
    }

    public static void main(String[] args){

    }
    public static void testQuery(){
        Object o= new MySqlQuery().queryValue("SELECT count(*) FROM emp WHERE id=?",new Object[]{1});
        System.out.println(o);
    }
    public static void testDML(){
        Emp e=new Emp();
        e.setId(1);
        // new MySqlQuery().delete(e);
        e.setAge(250);
        e.setDeptId(23);
        e.setEmpname("BeL");
        e.setHireDate(new Date(System.currentTimeMillis()));
        e.setSalary(8000.00);

        new MySqlQuery().update(e,new String[]{"age","deptId","empname"});
    }
    public static void testQueryRows(){
        List<Emp> list=new MySqlQuery().queryRows("SELECT id,empname,age FROM emp WHERE age>?",Emp.class,new Object[]{10});
        for(Emp emp:list){
            System.out.println(emp.getEmpname()+"----"+emp.getAge()+"-----"+emp.getHireDate()+"------"+emp.getDeptId());
        }

        String sql="SELECT e.id,e.age,e.empname,d.dname,d.address 'DeptAddr' FROM emp e " +
                "" +
                "JOIN dept d ON e.deptId=d.id";
        List<EmpVO> list2=new MySqlQuery().queryRows(sql,EmpVO.class,new Object[]{});
        for(EmpVO emp:list2){
            System.out.println(emp.getEmpname()+"----"+emp.getAge()+"-----"+emp.getDname()+"------"+emp.getDeptAddr()+"----"+emp.getId());
        }
    }
}
