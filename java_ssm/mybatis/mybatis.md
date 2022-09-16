# 关于MyBatis

前置知识

- JDBC
- Sql



![](/home/danny/Documents/notes/java_ssm/mybatis/mybatis_logo.png)

mybatis是持久层框架

mybatis源于是Apache的开源项目的iBatis

### 导入

1. MySQL驱动+mybatis+junit

   ```xml
   <dependency>
       <groupId>mysql</groupId>
       <artifactID>mysql-connector-java</artifactID>
       <version>5.1.47</version>
   </dependency>
   <dependency>
   	<groupId>or0g.mybatis</groupId>
   	<artifactId>mybatis</artifactId>
       <version>3.5.2</version>
   </dependency>
   <dependency>
   	<groupId>junit</groupId>
   	<artifactId>junit</artifactId>
   	<version>4.12</version>
   </dependency>
   ```


# 第一个mybatis程序

配置xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/><!--事务管理-->
            <dataSource type="POOLED">
                <property name="driver" value="数据库驱动"/>
                <!--url中&字符需要用&amp;转义-->
                <!--
				url = 驱动:数据库软件名://ip:port/数据库名?useSSL=[true|false]&amp;useUnicode=[true|false]&amp;characterEncoding="UTF-8 "
				-->
        		<property name="url" value="数据库url"/>
        		<property name="username" value="数据库用户名"/>
        		<property name="password" value="数据库用户密码"/>
      		</dataSource>
    	</environment>
  	</environments>
    <mappers>
    	<mapper resource = "com/danny/dao/userMapper.xml"></mapper>
    </mappers>
</configuration>
```

编写mybatis工具类

MyBatis以SqlSessionFactory为核心，SqlSessionFactory通过SqlSessionFactoryBuilder获得，SQLSessionFactoryBuilder可以通过xml配置文件或预先制定的Configuration的实例构建出SqlSessionFactory的实例

```java

public class MybatisUtil {
    private static SqlSessionFactory factory;
    static {
        try{
            String resource="mybatis-config.xml";
            InputStream inputStream = Resources.getR esourceAsStream(resource);
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession(){
        return factory.openSession();
    }
}

```

原先需要：

实体类，Dao接口，DaoImpl

用mybatis后：

实体类，Dao接口，mapper xml配置文件

mapper.xml

```xml

<！DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.danny.test.UserDao">
    <select id = "getUserList" resultType="com.danny.entity.User"></select>
</mapper>
```

# 增删改查

1.namespace

namespace中的包名要和Dao/mapper名相同

2.select标签

查询语句，id相当于对应namespace中接口的方法名相同，resultType：相当于SQL语句的返回值，parameterType参数类型

查：如有函数：

```java
User getUserById(int id);
```

配置xml：

```xml
<select id = "getUserById" parameterType="int" resultType="com.danny.pojo.User">
	select * from mybatis.user where id = #{id}
</select>
```

增：如有函数

```java
int addUser(User user);
```

配置xml:

```xml
<!--形参名可直接用于-->
<insert id = "addUser" parameterType="com.danny.pojo.User">
	insert into mybatis.user(id,name,pwd) values (#{id},#{name},#{pwd});
</insert>
```

改：如有函数：

```java
int updateUser(User user);
```

配置xml

```xml
<update id = "updateUser" parameterType="com.danny.pojo.User">
	update db.user set name = #{name},pwd=#{pwd} where id=#{i}
</update>
```

