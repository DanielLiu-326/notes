package com.mybatis.test;

import com.mybatis.dao.UserDaoMapper;
import com.mybatis.po.MyUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest1 {
	public static void main(String[] args) {
		try {
			//读取配置文件mybatis-config.xml
			InputStream config = Resources.getResourceAsStream("mybatis-config1.xml");
			//根据配置文件构建SqlSessionFactory
			SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(config);
			//通过SqlSessionFactory创建SqlSession
			SqlSession ss = ssf.openSession();
			//SqlSession执行映射文件中定义的SQL，并返回映射结果
			UserDaoMapper userDaoMapper = ss.getMapper(UserDaoMapper.class);
			//查询一个用户
			MyUser mu = userDaoMapper.selectUserById(1);
			System.	out.println(mu);
			//添加一个用户
			MyUser addmu = new MyUser();
			addmu.setUname("陈恒");
			addmu.setUsex("男");
			userDaoMapper.addUser(addmu);
			//修改一个用户
			MyUser updatemu = new MyUser();
			updatemu.setUid(1);
			updatemu.setUname("张三");
			updatemu.setUsex("女");
			userDaoMapper.updateUser(updatemu);
			//删除一个用户
			userDaoMapper.deleteUser(2);
			//查询所有用户
			List<MyUser> listMu = userDaoMapper.selectAllUser();
			for (MyUser myUser : listMu) {
				System.out.println(myUser);
			}
			//提交事务
			ss.commit();
			//关闭SqlSession
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
