package com.sorm.core;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.utils.JavaFileUtils;
import com.sorm.utils.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构
 */
public class TableContext {
    /**
     * 表名为key，表信息对象为value
     */
    public static Map<String,TableInfo> tables=new HashMap<String,TableInfo> ();

    /**
     * 将po的class对象和表信息对象关联起来，便于重用
     */
    public static Map<Class,TableInfo> poClassTableMap =new HashMap<Class, TableInfo>();

    private TableContext(){}

    static {
        try {
            //初始化获得表的结构
            Connection con=DBManager.getConn();
            DatabaseMetaData dbmd=con.getMetaData();//获得表中的所有信息

            ResultSet tableRet=dbmd.getTables(null,"%","%",new String[] {"TABLE"});
            while(tableRet.next()){
                String tableName=(String) tableRet.getObject("TABLE_NAME");

                TableInfo ti=new TableInfo(tableName,new ArrayList<ColumnInfo>(),new HashMap<String,ColumnInfo>());
                tables.put(tableName,ti);
                ResultSet set=dbmd.getColumns(null,"%",tableName,"%");//查询表中的所有字段
                while(set.next()){
                    ColumnInfo ci=new ColumnInfo(set.getString("COLUMN_NAME"),set.getString("TYPE_NAME"),0);
                    ti.getColumns().put(set.getString("COLUMN_NAME"),ci);
                }
                ResultSet set2=dbmd.getPrimaryKeys(null,"%",tableName);//查询t_user表中的主键
                while (set2.next()){
                    ColumnInfo ci2=(ColumnInfo) ti.getColumns().get(set2.getObject("COLUMN_NAME"));
                    ci2.setKeyType(1);//设置主键类型
                    ti.getPriKeys().add(ci2);
                }

                if(ti.getPriKeys().size()>0){
                    ti.setOnlyPriKey(ti.getPriKeys().get(0));//取唯一主键，方便使用，如果是联合主键，则为空！
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        //更新类结构
        updaJavaPOFile();

        //加载PO包下的类，便于重用
        loadPOTables();
    }

    /**
     * 根据表结构，更新配置的po包下面的java类
     * 实现了从表结构转化到类结构
     */
    public static void updaJavaPOFile(){
        Map<String,TableInfo> tables=TableContext.tables;
        for(TableInfo t:tables.values()){
            JavaFileUtils.createJavaPOFile(t,new MySqlTypeConvertor());
        }
    }

    /**
     * 加载PO包下面的类，
     */
    public static void loadPOTables(){
       // Class.forName("")
        for(TableInfo tableInfo:tables.values()){
            try{
               Class C=Class.forName("com.sorm.po."+ StringUtils.firstChar2UpperCase(tableInfo.getTname()));//配置文件无法读取，路径以字符串形式输入
               // System.out.println("this C=:"+C.toString());
               poClassTableMap.put(C,tableInfo);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

        }
    }


    public static void main(String[] args){
      //  Map<String,TableInfo> tables=TableContext.tables;

    }
}
