# 1.入门

![](/home/danny/Documents/notes/java_ssm/spring-mvc/spring_mvc_theory.png)

包含4个Spring MVC接口：DispatcherServlet、HandlerMapping、Controller和ViewResoler:

- 所有的请求都经过DispatcherServlet来统一分发。DispatcherServlet将请求分发给Controller之前，需要借助于Spring MVC提供的HandlerMapping定位到具体的Controller。
- HandlerMapping接口负责完成客户请求到Controller映射。
- Controller接口将处理用户请求，这和Java Servlet扮演的角色是一致的。一旦Controller处理完用户请求，则返回ModelAndView对象给DispatcherServlet前端控制器，ModelAndView中包含了模型（Model）和视图（View）。从宏观角度考虑，DispatcherServlet是整个Web应用的控制器；从微观考虑，Controller是单个Http请求处理过程中的控制器，而ModelAndView是Http请求过程中返回的模型（Model）和视图（View）。
- ViewResolver接口（视图解析器）在Web应用中负责查找View对象，从而将相应结果渲染给客户。

## 1.1 工作原理

- 1.用户通过浏览器向服务器发送请求，请求会被Spring MVC的前端控制器DispatcherServlet所拦截;
- 2.DispatcherServlet拦截到请求后，会调用HandlerMapping处理器映射器;
- 3.处理器映射器根据请求URL找到具体的处理器，生成处理器对象及处理器拦截器（如果有则生成）一并返回给DispatcherServlet;
- 4.DispatcherServlet会通过返回信息选择合适的HandlerAdapter（处理器适配器）;

- 5.HandlerAdapter会调用并执行Handler（处理器），这里的处理器指的就是程序中编写的Controller类，也被称之为后端控制器;

- 6.Controller执行完成后，会返回一个ModelAndView对象，该对象中会包含视图名或包含模型和视图名;
- 7.HandlerAdapter将ModelAndView对象返回给DispatcherServlet;
- 8.DispatcherServlet会根据ModelAndView对象选择一个合适的ViewReslover（视图解析器）;
- 9.ViewReslover解析后，会向DispatcherServlet中返回具体的View（视图）;
- 10.DispatcherServlet对View进行渲染（即将模型数据填充至视图中）;
- 11.视图渲染结果会返回给客户端浏览器显示。

## 1.2使用Spring-MVC

1.在web.xml中部署DispatcherServlet

```xml
<!--部署DispatcherServlet-->
<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- 表示容器在启动时立即加载servlet -->
    <load-on-startup>1</load-on-startup>
    <!--
	如果没有下面的的配置，在程序运行时会在WEB-INF下查找命名遵循servletName-servlet.xml的文件
	如果有，则加载给定的文件
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/spring-config/springmvc-servlet.xml</param-value>
 	</init-param>
-->
</servlet> 
<servlet-mapping>
	<servlet-name>springmvc</servlet-name>
	<!-- 处理所有URL-->
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

2.创建web应用程序首页

```html
<body>
	没注册的用户，请<a href="${pageContext.request.contextPath }/register">注册</a>！<br>
	已注册的用户，去<a href="${pageContext.request.contextPath }/login">登录</a>！
</body>
```

3.创建Controller类

RegisterController

```java
public class RegisterController implements Controller{
 	@Override
    public ModeAndView handleRequest(HttpServletRequest req,HttpServletResponse resp){
        return new ModeAndView("/WEB-INF/jsp/register.jsp");
        //Spring MVC控制器处理完请求后会返回一个ModelAndView对象
        //View表示使用何种视图及具体资源进行显示，Model用来存储在视图中显示的数据
    }
}
```

LoginController类

```java
public class LoginController implements Controller{
    @Override 
    public ModeAndView handleRequest(HttpServletRequest req,HttpServletResponse resp){
        return new ModeAndView("/WEB-INF/jsp/login.jsp");
    }
}
```

4.创建SpringMVC配置文件springmvc-servlet.xml，配置Controller映射

```xml
<?xml version = "1.0" endcoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns ="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
                           http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">
	<bean name = "/login" class="controller.LoginController"/>
    <bean name = "/register" class = "controller.RegisterController"/>
</beans>
```

5.创建应用的其他页面

包含/WEB-INF/jsp/login.jsp 和register.jsp

## 1.3 使用视图解析器

上面的例子没有使用试图控制器，在第3步时，直接给定了jsp的全部路径，不好

可以在springmvc-servlet.xml添加一个视图解析器

```xml
<bean class = "org.springframework.web.servlet.view.InternalResourceViewResolver"
      id= "internelResourceViewResolver">
	<property name="prefix" value="/WEB-INF/jsp/"/>
	<property name="suffix" value=".jsp"/>
</bean>
```

配置了试图控制器后，在Controller里面只需要给定：login或register即可，视图控制器会根据配置的前缀后缀自动拼接前端页面路径

# 2.Spring-MVC的Controller

controller有两种装配方式：

- 基于配置文件的装配方式 
- 基于注解的装配方式

这两种分别对应spring的装配方法。

## 2.1基于配置文件的Controller

第一章已经阐述了如何使用配置文件创建Controller。这种方式不经常被使用，更常用的方式是使用注解

## 2.2基于注解的Controller

前一章给出了使用xml文件配置的Controller，这种Controller需要实现Controller接口，而且每个Controller中只能实现一个方法。不够灵活。

这里给出第二种配置方法，使用注解

1.配置springmvc-servlet.xml

```xml
<beans 
       .....
       >
    <!--设置要扫描的包同applicationContext的配置相同-->
	<context:component-scan base-package = "controller"/>
    <bean class = "org.springframework.web.servlet.view.InternelResourceViewResolver"
          id = "internalResouceViewResolver"
          >
    	<!--前缀-->
        <property name = "prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name = "suffix" value = ".jsp"/>
    </bean>
</beans>
```

### @Controller

这个注解在spring衍生注解中讲过，作用同@Component

### @RequestMapping

参数

| 属性名    | 类型            | 描述                                                         |
| --------- | --------------- | ------------------------------------------------------------ |
| name      | String          | 可选属性，用于为映射地址指定别名                             |
| method    | String[]        | 可选属性，同时也是默认属性，用于映射一个其你去和一种方法，可以标注在一个方法或者类上。 |
| method    | RequestMethod[] | 可选属性，用于指定该方法用于处理那种类型的请求，方式，包含GET,HEAD,POST,OPTIONS,PUT,PATCH,DELETE,TRACE    例如：method={RequestMethod.GET} |
| params    | String[]        | 可选属性，用于指定Request中必须包含某些参数的值，才可以通过其标注方法处理 |
| headers   | String[]        | 可选属性，用于指定Request中必须好汉某些指定的header的值，才可以通过被标注的方法处理 |
| consumers | String[]        | 可选属性，用于指定处理请求的提交内容类型(Content-type)，比如application/json，text/html等； |
| produces  | String[]        | 可选属性，用于指定返回的内容类型，返回的内容类型必须是request请求头（accept）中包含的类型。 |

方法级别用法:

```java
@Controller
public class SomeController{
    @RequestMapping(value="/some/foo")
    public String foo(){
        return "foo";//转foo.jsp
    }
    @RequestMapping(value="/some/bar")
    public String bar(){
        return "bar";//转bar.jsp
    }
}
```

类级别用法，下面这种配置效果同上面的配置

```java
@Controller
@RequestMapping(value="/some")
public class SomeController{
    @RequestMapping(value="/foo")
    public String foo(){
        return "foo";//转foo.jsp
    }
    @RequestMapping(value="/bar")
    public String bar(){
        return "bar";//转bar.jsp
    }
}
```

关于请求处理方法：

请求处理方法的参数可以添加ServletApi类型，如HttpSession，HttpServletRequest等

​    除了Servlet API参数类型外，还有输入输出流、表单实体类、注解类型、与Spring框架相关的类型等等，这些类型在后续章节中使用时再详细介绍。

但特别重要的类型是org.springframework.ui.Model类型，该类型是一个包含Map的Spring框架类型。每次调用请求处理方法时，Spring MVC都将创建org.springframework.ui.Model对象。

以下是支持的类型列表：

```java


```

## 2.3 Controller请求处理方法

### 参数类型

下面是支持的参数类型：

```java
javax.servlet.ServletRequest / javax.servlet.http.HttpServletRequest
javax.servlet.ServletResponse / javax.servlet.http.HttpServletResponse
javax.servlet.http.HttpSession
org.springframework.web.context.request.WebRequest或
org.springframework.web.context.request.NativeWebRequest
java.util.Locale
java.util.TimeZone (Java 6+) / java.time.ZoneId (on Java 8)
java.io.InputStream / java.io.Reader
java.io.OutputStream / java.io.Writer
org.springframework.http.HttpMethod
java.security.Principal
@PathVariable、@MatrixVariable、@RequestParam、@RequestHeader、@RequestBody、@RequestPart、@SessionAttribute、@RequestAttribute注解
HttpEntity<?>
java.util.Map / org.springframework.ui.Model /org.springframework.ui.ModelMap
org.springframework.web.servlet.mvc.support.RedirectAttributes
org.springframework.validation.Errors /org.springframework.validation.BindingResult
org.springframework.web.bind.support.SessionStatus
org.springframework.web.util.UriComponentsBuilder
```

### 返回值类型

- ModelAndView：可以添加Model数据，并指定视图
- Model
- Map
- View
- String：可以跳转视图，但不能携带数据
- void：可以跳转视图，但不能携带数据
- HttpEntity\<?>或ResponseEntity<?>
- Callable<?>
- DeferredResult<?>

### 重定向和转发

```java
@RequestMapping("/user/login")
public String login(){
    //转发
    return "forward:/user/welcome";
}
@RequestMapping("/user/register")
public String register(UserInput input,HTTPSession,Model model){
    //do something
    //重定向
    return "redirect:/user/newUser"
}
```



## 2.4请求处理方法参数的数据绑定

### 通过Bean接收参数

创建用户输入类

```java
public class UserInput{
    private String name;
    private String password;
    //setter getter
    
}
```

控制器方法：

```java
@RequestMapping("/user/login")
public String login(UserInput input,HTTPSession,Model model){
    //do something
    return "/WEB-INF/jsp/main.jsp"
}

```

### 通过处理方法的形参

让http请求url中的参数名与函数的形参名相同

```java
@RequestMapping("/user/login")
public String login(String name,String password,HTTPSession,Model model){
    //do something
    return "/WEB-INF/jsp/main.jsp"
}
```

### 通过HttpServletRequest

```java
@RequestMapping("/user/login")
public String login(HttpServletRequest request,HTTPSession,Model model){
    String uname = request.getParameter("name");
    String password = request.getParameter("password");
    //do something
    return "/WEB-INF/jsp/main.jsp"
}
```

### 通过@PathVariable

```java
@RequestMapping("/user/login")
public String login(@PathVariable String name,@PathVariable password,HTTPSession,Model model){
    //do something
    return "/WEB-INF/jsp/main.jsp"
}
```

请求url需要：

http://localhost:8080/user/login/zhangsan/123456789

会将name绑定为"zhangsan"会将password绑定为"123456789"

### 通过@RequestParam



```java
@RequestMapping("/user/login")
public String login(@RequestParam String name,@RequestParam password,HTTPSession,Model model){
    //do something
    return "/WEB-INF/jsp/main.jsp"
}
```

当请求参数和接收参数不同时，可以为RequestParam添加参数如@RequestParam("userName") String name

请求url需要：

http://localhost:8080/user/login?name=zhangsan&password=123456789

这种方式同处理方法形参接收不同点在于：通过处理方法形参传参当请求参数与接收参数名不一致时不会报错，而本方法会返回400错误

### 通过@ModelAttribute

```java
public class UserInput{
    private String name;
    private String password;
    //setter getter
}
```

```java
@RequestMapping("/user/login")
public String login(@ModelAttribute("user") UserInput input,HTTPSession,Model model){
    //do something
    //使用ModelAttribute之后就不需要再使用model.addAttribute("input",input);了
    return "/WEB-INF/jsp/main.jsp"
}
```





1.创建index.jsp

```html
<body>
    <form action = "{pageContext.request.contextPath}/select" method="GET">
       	输入用户名：<input type="text" name = "username"><br/>
        <input type = "submit" value = "提交"/>
    </form>
</body>
```

2.创建持久化类

```java
public class User{
    private Integer id;
    private String name;
    private String sex;
 	//setter getter
}
```

3.创建dao层接口

```java
@Repository(UserDao)
public interface UserDao{
    public List<User> selectUserByName(String name);
}
```

4.创建Service层接口

```java
public interface UserService{
    public List<User> selectUserByName(String name);
}
```

5.创建Service实现类

```java
@Service
@Transactional
public class UserServiceImpl{
    @Autowired
    private UserDao dao;
	public List<User> selectUserByName(String name);
}
```

6.创建Controller层

```java
@Controller
@RequestMapping("/user")
public class UserController{
    @Autowired
    UserService userService;
 		   
    @RequestMapping("/select")
    public String select(String name,Model model){
        List<User> users = this.userService.selectUserByName(name);
        model.addAttributes("users",users);
        return "userList";
    }
}
```

7.创建Web.xml

```xml
<context-param>
	<param-name>contextConfigLocation</param-name>
    <param-value>classPath:applicationContext.xml</param-value>
</context-param>
<listener>
	<listener-class>
    	xxx
    </listener-class>
</listener>


<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>xxx</servlet-class>
	<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

8.创建springmvc-servletxml

```xml
<context:component-scan base-package="com.controller"/>
<bean 
      class=xxx
      id="internalResourceViewResolver">
	  <property name = "prefix" value = "/WEB-INF/jsp/">
      <property name = "suffix" value = ".jsp"/>
</bean>
```

9.创建mybatis-config.xml

```xml
<configuration>
    <mappers>
		<mapper resource="com/danny/dao/UserMapper.xml"/>
	</mappers>
</configuration>
```

10.创建UserMapper.xml

```xml
<mapper namespace="com.danny.dao.UserDao">
    <select id = "selectUserByName" resultType = "com.danny.pojo.User" parameterType="String">
        select * from users where name = #{name}
    </select>
</mapper>
```

11.创建applicationContext.xml

```xml
<beans>
	<bean id = "dataSource" class= xxx>
    	<property name = ""></property>
        .....
    </bean>
    <bean id = "txManager" class = xxx>
    	<property name = "dataSource" ref="dataSource"/>
    </bean>
    <bean id = "sqlSessionFactory" class = "">
    	<property name = "dataSource" ref = "dataSource"/>
        <property name = "configLocation" ref = "classpath:com/danny/dao/mybatis-config.xml"/>
    </bean>
    <bean class = "xxxMapperScannerConfigurer">
    	<property name = "basePackage" value = "com.danny.dao"></property>
        <property name = "sqlSessionFactoryBeanName" value = "sqlSessionFactory"/>
        <context:component-scan base-package="com.danny.service"></context:component-scan>
    </bean>
</beans>
```

