package test;

import autowired.domain.Item;
import autowired.domain.User;
import autowired.service.ItemService;
import autowired.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class AutowireBeanTest
{
	public static void main(String[] args)throws Exception
	{
		// 创建Spring容器
		ApplicationContext ctx = new
			ClassPathXmlApplicationContext("autowiredBeans.xml");
		UserService us = ctx.getBean("userService", UserService.class);
		us.addEntity(new User());
		ItemService is = ctx.getBean("itemService", ItemService.class);
		is.addEntity(new Item());
	}
}