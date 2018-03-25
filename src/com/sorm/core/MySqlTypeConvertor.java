package com.sorm.core;

/**
 * mysql数据类型金和java数据类型的转换
 */
public class MySqlTypeConvertor implements TypeConvertor {
    @Override
    public String databaseType2JavaType(String columnType) {

        //varchar --> String
        if("varchar".equalsIgnoreCase(columnType)||"char".equalsIgnoreCase(columnType)){
             return "String";
        }else if("int".equalsIgnoreCase(columnType)
                ||"tinyint".equalsIgnoreCase(columnType)
                ||"smallint".equalsIgnoreCase(columnType)
                ||"integer".equalsIgnoreCase(columnType)){
            return "Integer";
        }else if("bigint".equalsIgnoreCase(columnType)){
            return "Long";
        }else if("double".equalsIgnoreCase(columnType)){
            return "Double";
        }else if("float".equalsIgnoreCase(columnType)){
            return "Float";
        }else if("clob".equalsIgnoreCase(columnType)){
            return "java.sql.Clob";
        }else if("blob".equalsIgnoreCase(columnType)){
            return "java.sql.Blob";
        }else if("date".equalsIgnoreCase(columnType)){
            return "java.sql.Date";
        }else if("time".equalsIgnoreCase(columnType)){
            return "java.sql.Time";
        }else if("timestamp".equalsIgnoreCase(columnType)){
            return "java.sql.Timestamp";
        }

        return null;
    }

    @Override
    public String JavaType2DatabaseType(String columnType) {
        return null;
    }
}
