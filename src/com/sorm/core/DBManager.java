package com.sorm.core;

import com.sorm.bean.Configuration;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 根据配置信息，维持连接对象的管理（增加连接池功能）
 */
public class DBManager {
    private static Configuration conf;
    static { //静态代码块 加载一次
        Properties pros=new Properties();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf =new Configuration();
        conf.setDriver(pros.getProperty("driver"));
        conf.setPoPackage("com\\sorm\\po");
        conf.setPsw(pros.getProperty("psw"));
        conf.setUser(pros.getProperty("user"));
        conf.setUrl(pros.getProperty("url"));
        conf.setUsingDB(pros.getProperty("usingDB"));
        conf.setSrcPath("srcPath");
    }
    public static Connection getConn(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/sorm?useSSL=true","root","8404"); //直接建立连接，后期增加连接池处理，提高效率！
        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet set){
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

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
    }
    public static void close(Connection connection){
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void close(Connection connection, PreparedStatement preparedStatement){
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            if (preparedStatement!=null){
                preparedStatement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
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

