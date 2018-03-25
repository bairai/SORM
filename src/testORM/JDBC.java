package testORM;

import java.sql.*;

public class JDBC {
    Connection connection=null;
    PreparedStatement ps =null;
    ResultSet rs=null;




    public static Connection mysqlconn(){
        String mysqlURL = "jdbc:mysql://localhost:3306/sorm?useSSL=false";
        String username = "root";
        String psw = "8404";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(mysqlURL,username,psw);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public static void close(Connection connection,PreparedStatement preparedStatement,ResultSet set){
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
}
