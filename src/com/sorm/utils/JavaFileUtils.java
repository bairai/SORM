package com.sorm.utils;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.JavaFiledGetSet;
import com.sorm.bean.TableInfo;
import com.sorm.core.DBManager;
import com.sorm.core.MySqlTypeConvertor;
import com.sorm.core.TableContext;
import com.sorm.core.TypeConvertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装了 生成 Java文件（源代码）常用的操作
 * @author fanghaoda
 */
public class JavaFileUtils {
    /**
     * 根据字段信息生成java属性信息，如：varchar username-- private String username；以及相应的setget的方法
     * @param columnInfo 字段信息
     * @param convertor 类型转化器
     * @return java属性和set/get方法
     */
    public static JavaFiledGetSet createFieldGetSetSRC(ColumnInfo columnInfo, TypeConvertor convertor){

        JavaFiledGetSet jfgs=new JavaFiledGetSet();

        String JavaFiledType=convertor.databaseType2JavaType(columnInfo.getDataTyype());

        jfgs.setFieldInfo("\tprivate "+JavaFiledType+" "+columnInfo.getName()+";\n");

        /*
        public String getUsername(){
        return Username;}
        生成get方法
        */
        StringBuilder getSrc=new StringBuilder();
        getSrc.append("\tpublic "+JavaFiledType+"  get"+StringUtils.firstChar2UpperCase(columnInfo.getName())+"(){\n");
        getSrc.append("return "+columnInfo.getName()+";\n");
        getSrc.append("\t}\n");
        jfgs.setGetInfo(getSrc.toString());

        //public void setUsername(String username){
        //this.username=username;
        //}
        //生成set方法
        StringBuilder setSrc=new StringBuilder();
        setSrc.append("\tpublic void set"+StringUtils.firstChar2UpperCase(columnInfo.getName())+"(");
        setSrc.append(JavaFiledType+" "+columnInfo.getName()+"){\n");
        setSrc.append("\t\tthis."+columnInfo.getName()+"="+columnInfo.getName()+";\n");
        setSrc.append("\t}\n");
        jfgs.setSetInfo(setSrc.toString());

        return jfgs;
    }

    /**
     * 根据表信息生成java类的源代码
     * @param tableInfo 表信息
     * @param typeConvertor 数据类型转化器
     * @return
     */
    public static String createJavaSrc(TableInfo tableInfo,TypeConvertor typeConvertor){
        Map<String,ColumnInfo> columns=tableInfo.getColumns();
        List<JavaFiledGetSet> javaFields=new ArrayList<JavaFiledGetSet>();

        for(ColumnInfo c:columns.values()){
            javaFields.add(createFieldGetSetSRC(c,typeConvertor));
        }

        StringBuilder src=new StringBuilder();

        //生成paceage语句
        src.append("package com.sorm.po;\n\n");

        //生成import语句
        src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n");

        //生成类声明语句
        src.append("public class "+StringUtils.firstChar2UpperCase(tableInfo.getTname())+"{\n\n");

        //生成属性列表
        for(JavaFiledGetSet f:javaFields){
            src.append(f.getFieldInfo());
        }

        //生成get方法列表
        for(JavaFiledGetSet f:javaFields){
            src.append(f.getGetInfo());
        }
        //生成set方法列表
        for(JavaFiledGetSet f:javaFields){
            src.append(f.getSetInfo());
        }
        //生成 类的结束
        src.append("}\n");
        return src.toString();
    }

    /**
     * 在目录下创建.java源文件
     * @param tableInfo 表信息
     * @param typeConvertor 格式转换器
     */
    public static void createJavaPOFile(TableInfo tableInfo,TypeConvertor typeConvertor){
        String src=createJavaSrc(tableInfo,typeConvertor);

        //发现idea调用配置文件一直失败，能利用system out打印出来，但
        String srcPath="C:\\Users\\Administrator\\IdeaProjects\\SORM\\";
                //DBManager.getConf().getSrcPath()+"\\";
        String packagePath="com\\sorm\\po";
                //DBManager.getConf().getPoPackage().replaceAll("\\.","/");

        File f=new File(("C:\\Users\\Administrator\\IdeaProjects\\SORM\\src\\com\\sorm\\po").replaceAll("\\.","/"));
     //   System.out.println(f);

        //如果指点目录不存在，则帮用户创建目录
        if(!f.exists()){
            f.mkdirs();
        }

        BufferedWriter bw=null;

        try{
            bw=new BufferedWriter(new FileWriter(f.getAbsoluteFile()+"/"
                    +StringUtils.firstChar2UpperCase(tableInfo.getTname())+".java"));
            bw.write(src);
            System.out.println("建立表"+tableInfo.getTname()+"对应的java类： "
                    +StringUtils.firstChar2UpperCase(tableInfo.getTname())+".java");

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

 //   public static void main(String[] args){
//       ColumnInfo ci=new ColumnInfo("username","varchar",0);
//        JavaFiledGetSet jf= createFieldGetSetSRC(ci,new MySqlTypeConvertor());
//       System.out.println(jf.toString());
//       Map<String,TableInfo> map=TableContext.tables;
//      for(TableInfo t:map.values()){
//          createJavaPOFile(t,new MySqlTypeConvertor());
//
//      }



 //   }
}
