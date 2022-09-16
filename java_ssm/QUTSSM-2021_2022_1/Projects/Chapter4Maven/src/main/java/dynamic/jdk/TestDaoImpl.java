package dynamic.jdk;
import org.springframework.stereotype.Repository;
@Repository("testDao") //<bean id="testDao" class="dynamic.jdk.TestDaoImpl"/>
public class TestDaoImpl implements TestDao{
	@Override
	public int save(String arg1) {
//		int n = 100/0;
		System.out.println("保存");
		System.out.println("arg1:"+arg1);
		return 1;
	}
	@Override
	public void modify() {
		System.out.println("修改");
	}
	@Override
	public void delete() {
		System.out.println("删除");
	}
}
