package com.sorm.utils;

/**
 * 封装了字符串常用的操作
 * @author fanghaoda
 */
public class StringUtils {
    /**
     * 将，目标字符串首字母变成大写
     * @param stc 目标字符串
     * @return 首字母为大写的目标字符串
     */
    public static String firstChar2UpperCase(String stc){
        //abcd-->Abcd

        //abcd --ABCD ---A
        return stc.toUpperCase().substring(0,1)+stc.substring(1);

    }
}
