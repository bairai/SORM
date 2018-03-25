package com.sorm.core;

/**
 * 负责java数据类型和数据库数据类型的相互转换
 */
public interface TypeConvertor {
    /**
     * 将数据库数据类型转化成java数据类型
     * @param columnType 数据库字段的数据类型
     * @return java的数据类型
     */
    public String databaseType2JavaType(String columnType);

    /**
     * 将java数据类型转化成数据库数据类型
     * @param columnType java字段的数据类型
     * @return 数据库的数据类型
     */
    public String JavaType2DatabaseType(String columnType);


}
