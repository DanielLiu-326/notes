package test;

import lifecycleInit.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.core.io.ClassPathResource;


/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:    
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class BeanTestLifecycleInit
{
	public static void main(String[] args)throws Exception
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beanLifecycleInit.xml");
//		BeanFactory ctx = new XmlBeanFactory(new ClassPathResource("beanLifecycleInit.xml"));
		Person p = ctx.getBean("chinese" , Person.class);
		p.useAxe();
	}
}