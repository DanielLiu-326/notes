package dao;

import org.springframework.stereotype.Service;

public class TestDaoImpl implements TestDao{
	@Override
	public void sayHello() {
		System.out.println("Hello, Study hard!");
	}
}
