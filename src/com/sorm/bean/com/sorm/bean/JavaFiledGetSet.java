package com.sorm.bean;

/**
 * 封装了java属性和get、set方法的源代码
 */
public class JavaFiledGetSet {
    /**
     * 属性的源码信息：如 private int userId；
     */
    private String fieldInfo;
    /**
     * get方法的源码信息，如 public int get userID（）{}
     */
    private String getInfo;
    /**
     * set方法的源码信息，如 public void setUserId（int id）{this.id=id;}
     */
    private String setInfo;

    public String getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public String getGetInfo() {
        return getInfo;
    }

    public void setGetInfo(String getInfo) {
        this.getInfo = getInfo;
    }

    public String getSetInfo() {
        return setInfo;
    }

    public void setSetInfo(String setInfo) {
        this.setInfo = setInfo;
    }

    public JavaFiledGetSet(String fieldInfo, String getInfo, String setInfo) {
        super();
        this.fieldInfo = fieldInfo;
        this.getInfo = getInfo;
        this.setInfo = setInfo;
    }

    public JavaFiledGetSet() {
    }

    @Override
    public String toString() {
        return "JavaFiledGetSet{" +
                "fieldInfo='" + fieldInfo + '\'' +
                ", getInfo='" + getInfo + '\'' +
                ", setInfo='" + setInfo + '\'' +
                '}';
    }
}

