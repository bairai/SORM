在src下建立db.properties文件
（我在properties这个包中建立了，但是IDEA一直调用不到，
于是所有关于配置文件的内容基本是手打上去没有作引用，之后的版本会把这个问题解决
方案一是换个编译器，通过git传到另一台电脑
方案二是写一个专门放参数的全局类，以后改参数就可以直接在上面改）

每张表只能有一个主键（局限性，后期需要添加连接池等操作）

有个缺点要改进的：MySqlQuery 要引进 产生的类包（com.sorm.po），后面看看能不能在运行时（前）加载进去。

目前只能处理数据库来维护自增主键的方式（id AUTOINCREANT KEY ）

目前版本 1.0
主要功能是 联系数据库和java程序，java程序可以根据数据库里的表生成相应的类，
可以通过 new 一个新对象来 对数据库里面的数据进行控制（增删改查）
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

