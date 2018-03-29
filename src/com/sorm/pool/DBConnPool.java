package com.sorm.pool;


import com.sorm.core.DBManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *连接池的类
 * @author haoda
 *
 */
public class DBConnPool {
    /**
     * 连接池对象
     */
    private  List<Connection> pool;
   /**
     * 最大连接数
     */
    private static final int POOL_MAX_SIZE=DBManager.getConf().getPoolMaxSize();
    /**
     * 最小连接数
     */
    private static final int POOL_MIN_SIZE=DBManager.getConf().getPoolMinSize();

    /**
     * 初始化连接池，使池中的连接数达到最小值
     */
    public void initPool(){
        if(pool==null){
            pool=new ArrayList<Connection>();
        }
        while(pool.size()< POOL_MIN_SIZE){
            pool.add(DBManager.createConn());
            System.out.println("初始化池，池中连接数： "+pool.size());
        }
    }

    /**
     * 从连接池中取连接
     * @return 获得最后一个连接
     */
    public synchronized Connection getConnection(){
        int last_index=pool.size()-1;
        Connection connection= pool.get(last_index);
        pool.remove(last_index);//将最后一个连接从池中删去
        return  connection;
    }

    /**
     * 将连接放回连接池中
     * @param connection 要放回池中的连接
     */
    public  synchronized void close(Connection connection){
        if(pool.size()>= POOL_MAX_SIZE){
            try {
                if(connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            pool.add(connection);
        }
    }




    public DBConnPool(){
        initPool();
    }

}
