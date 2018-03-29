package com.sorm.core;

import com.sorm.Global.GlobalSet;
import com.sorm.bean.Configuration;
import com.sorm.pool.DBConnPool;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 根据配置信息，维持连接对象的管理（增加连接池功能）
 */
public class DBManager {
    /**
     * 配置文件的参数属性
     */
    private static Configuration conf;
    /**
     * 连接池对象
     */
    private static DBConnPool pool;
    static { //静态代码块 加载一次
        Properties properties=new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        conf =new Configuration();
        conf.setDriver(properties.getProperty("driver"));
        conf.setPoPackage(properties.getProperty("poPackage"));
        conf.setPsw(properties.getProperty("psw"));
        conf.setUser(properties.getProperty("user"));
        conf.setUrl(properties.getProperty("url"));
        conf.setUsingDB(properties.getProperty("usingDB"));
        conf.setSrcPath(properties.getProperty("srcPath"));
        conf.setQueryClass(properties.getProperty("queryClass"));
        conf.setPoolMaxSize(Integer.getInteger(properties.getProperty("POOL_MAX_SIZE")));
        conf.setPoolMinSize(Integer.getInteger(properties.getProperty("POOL_MIN_SIZE")));

        try {
            Class.forName("com.sorm.core.TableContext");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得Connection对象
     * @return Connection对象
     */
    public static Connection getConn(){
        if(pool==null){
            pool=new DBConnPool();
        }
        return pool.getConnection();
    }
    /**
     * 创建新的Connection对象
     * @return Connection对象
     */
    public static Connection createConn(){
        try {
            Class.forName(conf.getDriver());
            return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPsw()); //直接建立连接，后期增加连接池处理，提高效率！//password无法被正确引用
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接对象
     * @param connection 连接对象
     * @param preparedStatement 带参的sql语句
     * @param set 结果集
     */
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet set){

        try {
            if (preparedStatement!=null){
                preparedStatement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            if(set!=null){
                set.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        pool.close(connection);
    }
    public static void close(Connection connection){
            pool.close(connection);
    }

    /**
     * 关闭传入的连接对象
     * @param connection 连接对象
     * @param preparedStatement sql语句
     */
    public  static void close(Connection connection, PreparedStatement preparedStatement){

        try {
            if (preparedStatement!=null){
                preparedStatement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        pool.close(connection);
    }
    public static void close(ResultSet set){
        try {
            if(set!=null){
                set.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static Configuration getConf(){
        return  conf;
    }
}

