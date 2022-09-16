package aspectj.xml;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import dynamic.jdk.TestDao;
public class XMLAspectJTest {
	public static void main(String[] args) {
		ApplicationContext appCon = new ClassPathXmlApplicationContext("XmlApplicationContext.xml");
		//从容器中，获取增强后的目标对象
		TestDao testDaoAdvice = (TestDao)appCon.getBean("testDao");
		//执行方法
		System.out.println(testDaoAdvice.save("111"));
	}
}
