package com.sorm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 封装了JDBC查询常用的操作
 * @author fanghaoda
 */
public class JDBCUtils {
    /**
     * 为SQL设置参数
     * @param ps 安全的sql语句
     * @param params sql要配置的参数
     */
    public static void handleParams(PreparedStatement ps,Object[] params){
        try {
            if(params!=null){
                for(int i=0;i<params.length;i++){
                    //为sql设置参数
                    ps.setObject(1+i,params[i]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
