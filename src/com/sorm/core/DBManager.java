package com.sorm.core;

import com.sorm.Global.GlobalSet;
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
        conf =new Configuration();
        conf.setDriver(GlobalSet.driver);
        conf.setPoPackage(GlobalSet.poPackage);
        conf.setPsw(GlobalSet.psw);
        conf.setUser(GlobalSet.user);
        conf.setUrl(GlobalSet.url);
        conf.setUsingDB(GlobalSet.usingDB);
        conf.setSrcPath(GlobalSet.srcPath);
    }
    public static Connection getConn(){
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

