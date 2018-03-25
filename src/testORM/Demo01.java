package testORM;

import javafx.beans.binding.ObjectBinding;

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
public class Demo01 {
    public static void main(String[] args){
        Connection conn=JDBC.mysqlconn();
        PreparedStatement ps=null;
        ResultSet rs=null;

        //List<Object[]> list =new ArrayList<Object[]>();
        List<Map> list=new ArrayList<Map>();
        Map<String,Object> row=new HashMap<String, Object>();
        try {
             ps=conn.prepareStatement("SELECT empname,salary,age FROM emp where id>?");
             ps.setInt(1,0);
             rs=ps.executeQuery();

            while(rs.next()) {

                //Object [] obj=new Object[3];//one columnindex
                //    System.out.println("id=1 " + rs.getString(1) + "--" + rs.getInt(2) + "--" + rs.getInt(3));
                // obj[0]=rs.getString(1);
                // obj[1]=rs.getDouble(2);
                // obj[2]=rs.getInt(3)
                // list.add(obj);

                row.put("empname",rs.getObject(1));
                row.put("salary",rs.getObject(2));
                row.put("age",rs.getObject(3));
                list.add(row);
             }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBC.close(conn,ps,rs);
        }

        for (Map<String,Object> r :list) {
            for(String key:r.keySet()){
                System.out.println(key+"--"+r.get(key));
            }

        }
    }
}
