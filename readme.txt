
目前版本 1.1
主要功能是 联系数据库和java程序，java程序可以根据数据库里的表生成相应的类，
可以通过 new 一个新对象来 对数据库里面的数据进行控制（增删改查）

基本思想：表结构跟类对应；表中字段和类属性对应；表中记录和对象对应

例：更新主键为1的数据库数据，pramas是需要更新的属性
public static void main(String[] args){
       Emp e=new Emp();
               e.setId(1); //设置主键
               // new MySqlQuery().delete(e);
               e.setAge(250);
               e.setDeptId(23);
               e.setEmpname("BeL");
               e.setHireDate(new Date(System.currentTimeMillis()));
               e.setSalary(8000.00);
               new MySqlQuery().update(e,new String[]{"age","deptId","empname"});
}

