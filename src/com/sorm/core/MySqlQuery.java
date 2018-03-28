package com.sorm.core;

import com.sorm.po.*;
import com.sorm.vo.EmpVO;
import java.sql.*;
import java.util.List;

public class MySqlQuery extends Query {

    public static void main(String[] args){
        testQuery();
    }
    public static void testQuery(){
        Object o= new MySqlQuery().queryValue("SELECT count(*) FROM emp WHERE id=?",new Object[]{1});
        System.out.println(o.getClass());
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

    @Override
    public Object queryPagenate(int pageNum, int size) {
        return null;
    }
}
