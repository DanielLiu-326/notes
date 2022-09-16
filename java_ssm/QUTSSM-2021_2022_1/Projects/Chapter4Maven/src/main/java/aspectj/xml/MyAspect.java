package aspectj.xml;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
/**
 * 切面类，在此类中编写各种类型通知
 */
public class MyAspect {
	/**
	 * 前置通知，使用Joinpoint接口作为参数获得目标对象信息
	 */
	public void before(JoinPoint jp) {
		System.out.print("Before前置通知：模拟权限控制");
		System.out.println("，目标类对象：" + jp.getTarget() 
		+ "，被增强处理的方法：" + jp.getSignature().getName());
	} 
	/**
	 * 后置返回通知
	 */
	public void afterReturning(JoinPoint jp) {
		System.out.print("AfterReturning后置返回通知：" + "模拟删除临时文件");
		System.out.println("，被增强处理的方法：" + jp.getSignature().getName());
	}
	/**
	 * 环绕通知
	 * ProceedingJoinPoint是JoinPoint子接口，代表可以执行的目标方法
	 * 返回值类型必须是Object
	 * 必须一个参数是ProceedingJoinPoint类型
	 * 必须throws Throwable
	 */
   public void xxxx(String food, Integer time){}


	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		//开始
		System.out.println("Around环绕开始：执行目标方法前，模拟开启事务");
		//执行当前目标方法
		Object obj = pjp.proceed(new Object[]{"新修改的方法调用参数"});
		//结束
		System.out.println("Around环绕结束：执行目标方法后，模拟关闭事务");
		return 10;
	}
	/**
	 * 异常通知
	 */
	public void except(Throwable e) {
		System.out.println("Exception异常通知：" + "程序执行异常" + e.getMessage());
	}
	
	/**
	 * 后置（最终）通知
	 */
	public void after() {
		System.out.println("After最终通知：模拟释放资源");
	}
	
}
