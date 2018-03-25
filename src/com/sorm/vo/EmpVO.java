package com.sorm.vo;

public class EmpVO {
//    SELECT e.id,e.age,e.empname,d.dname,d.address FROM emp e
//    JOIN dept d ON e.deptId=d.id;
    private Integer id;
    private String empname;
    private Double xinshui;
    private Integer age;
    private String deptAddr;
    private String dname;

    public EmpVO(Integer id, String empname, Double xinshui, Integer age, String deptAddr, String dname) {
        this.id = id;
        this.empname = empname;
        this.xinshui = xinshui;
        this.age = age;
        this.deptAddr = deptAddr;
        this.dname = dname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public EmpVO() {
    }

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

    public Double getXinshui() {
        return xinshui;
    }

    public void setXinshui(Double xinshui) {
        this.xinshui = xinshui;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDeptAddr() {
        return deptAddr;
    }

    public void setDeptAddr(String deptAddr) {
        this.deptAddr = deptAddr;
    }
}
