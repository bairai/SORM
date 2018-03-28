package com.sorm.core;

/**
 * 创建Query对象的工厂类
 */
public class QueryFactory {
    /**
     * 克隆模式,
     */
    private static QueryFactory factory=new QueryFactory();
    private static Query prototypeObj;//模型对象
    static {
        try {
           Class c=Class.forName(DBManager.getConf().getQueryClass());//加载指定的query类
            prototypeObj=(Query) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private QueryFactory(){}//私有构造器



    public static Query createQuery(){
        try {
            return (Query) prototypeObj.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }



}
