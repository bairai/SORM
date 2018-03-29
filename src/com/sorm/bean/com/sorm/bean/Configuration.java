package com.sorm.bean;
/**
 * 管理配置信息
 */
public class Configuration {
   /**
    * 驱动类
    */
   private String driver;
   /**
    * JDBC的URL
    */
   private String  url;
   /**
    * 数据库的用户名
    */
   private String user;
   /**
    * 数据库的密码
    */
   private String psw;
   /**
    *使用的数据库类型
    */
   private String usingDB;
   /**
    * 项目的源码路径
    */
   private String srcPath;
   /**
    * 扫描生成JAVA类的包（po的意思是 ）
    */
   private String  poPackage;
   /**
    * 项目使用的查询类的路径
    */
   private String queryClass;
   /**
    * 连接池最小连接数
    */
   private int poolMinSize;
   /**
    * 连接池最大连接数
    */
   private int poolMaxSize;

   public String getDriver() {
      return driver;
   }

   public void setDriver(String driver) {
      this.driver = driver;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getQueryClass() {
      return queryClass;
   }

   public void setQueryClass(String queryClass) {
      this.queryClass = queryClass;
   }

   public String getUser() {
      return user;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public String getPsw() {
      return psw;
   }

   public void setPsw(String psw) {
      this.psw = psw;
   }

   public String getUsingDB() {
      return usingDB;
   }

   public void setUsingDB(String usingDB) {
      this.usingDB = usingDB;
   }

   public String getSrcPath() {
      return srcPath;
   }

   public void setSrcPath(String srcPath) {
      this.srcPath = srcPath;
   }

   public String getPoPackage() {
      return poPackage;
   }

   public void setPoPackage(String poPackage) {
      this.poPackage = poPackage;
   }

   public int getPoolMinSize() {
      return poolMinSize;
   }

   public void setPoolMinSize(int poolMinSize) {
      this.poolMinSize = poolMinSize;
   }

   public int getPoolMaxSize() {
      return poolMaxSize;
   }

   public void setPoolMaxSize(int poolMaxSize) {
      this.poolMaxSize = poolMaxSize;
   }

   public Configuration() {
   }

   public Configuration(String driver, String url, String user, String psw, String usingDB, String srcPath, String poPackage, String queryClass, int poolMinSize, int poolMaxSize) {
      this.driver = driver;
      this.url = url;
      this.user = user;
      this.psw = psw;
      this.usingDB = usingDB;
      this.srcPath = srcPath;
      this.poPackage = poPackage;
      this.queryClass = queryClass;
      this.poolMinSize = poolMinSize;
      this.poolMaxSize = poolMaxSize;
   }
}
