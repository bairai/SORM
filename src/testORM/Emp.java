package testORM;

import java.sql.Date;

public class Emp {
    private Integer id;
    private  String empname;
    private  double salary;
    private Date hireDate;
    private Integer age;
    private Integer deptId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Emp(Integer id, String empname, double salary, Date hireDate, Integer age, Integer deptId) {
        this.id = id;
        this.empname = empname;
        this.salary = salary;
        this.hireDate = hireDate;
        this.age = age;
        this.deptId = deptId;
    }

    public Emp(String empname, double salary, Date hireDate, Integer age, Integer deptId) {
        this.empname = empname;
        this.salary = salary;
        this.hireDate = hireDate;
        this.age = age;
        this.deptId = deptId;
    }

    public Emp() {
    }
}
