package com.controller;
import com.dao.UserDao;
import com.po.MyUser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestController0 {
	public static void main(String[] args) {
		ApplicationContext appCon = new ClassPathXmlApplicationContext("applicationContext0.xml");
		UserDao userDao = appCon.getBean(UserDao.class);
		MyUser user1 = userDao.selectUserById(1);
		System.out.println(user1);
	}
}
