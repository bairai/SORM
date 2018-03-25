package com.sorm.po;

import java.sql.*;
import java.util.*;
public class Emp{

	private String empname;
	private java.sql.Date hireDate;
	private Integer deptId;
	private Integer id;
	private Double salary;
	private Integer age;
	public String  getEmpname(){
return empname;
	}
	public java.sql.Date  getHireDate(){
return hireDate;
	}
	public Integer  getDeptId(){
return deptId;
	}
	public Integer  getId(){
return id;
	}
	public Double  getSalary(){
return salary;
	}
	public Integer  getAge(){
return age;
	}
	public void setEmpname(String empname){
		this.empname=empname;
	}
	public void setHireDate(java.sql.Date hireDate){
		this.hireDate=hireDate;
	}
	public void setDeptId(Integer deptId){
		this.deptId=deptId;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setSalary(Double salary){
		this.salary=salary;
	}
	public void setAge(Integer age){
		this.age=age;
	}
}
