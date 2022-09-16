package instance;
public class BeanClass {	
	public String message;
	String beanName;

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public BeanClass() {
		message = "构造方法实例化Bean";
	}
	public BeanClass(String s) {
		message = s;
	}
}
