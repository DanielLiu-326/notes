package com.aop.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aop.dao.TestDao;
@Service("testService")
@Transactional
//加上注解@Transactional,就可以指定这个类需要受Spring的事务管理
//注意@Transactional只能针对public属性范围内的方法添加
public class TestServiceImpl implements TestService{
	@Autowired
	private TestDao testDao;
	@Override
	public int save(String sql, Object[] param) {
		return testDao.save(sql, param);
	}
	@Override
	public int delete(String sql, Object[] param) {
		return testDao.delete(sql, param);
	}

	public void test() {
		String deleteSql = "delete from user";
		String saveSql = "insert into user values(?,?,?)";
		Object param[] = {1, "chenheng", "男"};
		try {
			testDao.delete(deleteSql, null);
			testDao.save(saveSql, param);
			//插入两条主键重复的数据
			testDao.save(saveSql, param);
		} catch (Exception e) {
//			message = "主键重复，事务回滚！";
			e.printStackTrace();
			throw e;
		}
	}
}
