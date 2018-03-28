package com.sorm.test;

import com.sorm.core.MySqlQuery;
import com.sorm.core.Query;
import com.sorm.core.QueryFactory;
import com.sorm.po.Emp;
import com.sorm.vo.EmpVO;

import java.util.List;

/**
 * 客户端调用的test类
 */

public class Test {
    public static void main(String[] args) {
        Query q= QueryFactory.createQuery();
        List<Emp> list=q.queryRows("SELECT id,empname,age FROM emp WHERE age>?",Emp.class,new Object[]{10});
        for(Emp emp:list){
            System.out.println(emp.getEmpname()+"----"+emp.getAge()+"-----"+emp.getHireDate()+"------"+emp.getDeptId());
        }

        String sql="SELECT e.id,e.age,e.empname,d.dname,d.address 'DeptAddr' FROM emp e " +
                "" +
                "JOIN dept d ON e.deptId=d.id";
        List<EmpVO> list2=q.queryRows(sql,EmpVO.class,new Object[]{});
        for(EmpVO emp:list2){
            System.out.println(emp.getEmpname()+"----"+emp.getAge()+"-----"+emp.getDname()+"------"+emp.getDeptAddr()+"----"+emp.getId());
        }
    }
}
