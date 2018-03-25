package testORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * test Object
 */
public class Demo04 {
    public static void main(String[] args){
        Connection conn=JDBC.mysqlconn();
        PreparedStatement ps=null;
        ResultSet rs=null;
        Emp emp=null;
        List<Emp> list=new ArrayList<Emp>();

        try {
             ps=conn.prepareStatement("SELECT id,empname,salary,hireDate,age,deptId FROM emp where id>?");
             ps.setInt(1,1);
             rs=ps.executeQuery();

            while(rs.next()) {
             emp=new Emp(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getDate(4),rs.getInt(5),rs.getInt(6));
             list.add(emp);
             }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBC.close(conn,ps,rs);
        }

       for(Emp ep:list){
            System.out.println(ep.getHireDate());
       }
    }
}
