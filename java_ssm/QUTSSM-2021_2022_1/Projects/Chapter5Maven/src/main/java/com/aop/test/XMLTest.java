package com.aop.test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.aop.controller.StatementController;
public class XMLTest {
	public static void main(String[] args) {
		ApplicationContext appCon = new ClassPathXmlApplicationContext("XMLstatementapplicationContext.xml");
		StatementController ct = (StatementController)appCon.getBean("statementController");
		String result = ct.test();
		System.out.println(result);
	}
}
