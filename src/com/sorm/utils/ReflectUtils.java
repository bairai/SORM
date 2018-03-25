package com.sorm.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 封装了反射常用的操作
 * @author fanghaoda
 */
public class ReflectUtils {
    /**
     * 调用Obj的Get方法
     * @param fieldName 需要Get的属性
     * @param obj 被调用的对象
     * @return
     */
    public static Object invokeGet(String fieldName,Object obj){
        try {
            Class c=obj.getClass();
            Method m=c.getDeclaredMethod("get"+StringUtils.firstChar2UpperCase(fieldName),null);
            return m.invoke(obj,null);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static  void invokeSet(Object obj,String coulumnName,Object columnValue){

        Method M= null;
        try {
            M = obj.getClass().getDeclaredMethod("set"+ StringUtils.firstChar2UpperCase(coulumnName),
                    columnValue.getClass());
                M.invoke(obj,columnValue);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}


