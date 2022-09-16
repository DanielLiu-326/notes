package com.aop.test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.aop.controller.StatementController;
public class AnnotationTest {
	public static void main(String[] args) {
		ApplicationContext appCon = new ClassPathXmlApplicationContext("annotationstatementapplicationContext.xml");
		StatementController ct = (StatementController)appCon.getBean("statementController");
		String result = ct.test();
		System.out.println(result);
	}
}
