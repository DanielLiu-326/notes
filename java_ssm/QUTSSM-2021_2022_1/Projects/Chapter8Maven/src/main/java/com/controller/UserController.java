package com.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.dao.UserDao;
import com.po.MyUser;
@Controller("userController")
public class UserController {
	@Autowired
	private UserDao userDao;
	public void test() {
//		//使用if元素查询用户信息
//		MyUser ifmu = new MyUser();
//		ifmu.setUname("张");
//		ifmu.setUsex("女");
//
//		List<MyUser> listByif = userDao.selectUserByIf(ifmu);
//		System.out.println("if元素================");
//		for (MyUser myUser : listByif) {
//			System.out.println(myUser);
//		}


//		//使用choose元素查询用户信息
//		MyUser choosemu = new MyUser();
//		choosemu.setUname("陈");
//		choosemu.setUsex("女");
//		List<MyUser> listByChoose = userDao.selectUserByChoose(choosemu);
//		System.out.println("choose元素================");
//		for (MyUser myUser : listByChoose) {
//			System.out.println(myUser);
//		}
//
//
//		//使用trim元素查询用户信息
//		MyUser trimmu = new MyUser();
//		trimmu.setUname("陈");
//		trimmu.setUsex("男");
//
//		List<MyUser> listByTrim = userDao.selectUserByTrim(trimmu);
//		System.out.println("trim元素================");
//		for (MyUser myUser : listByTrim) {
//			System.out.println(myUser);
//		}
//
//
//		//使用where元素查询用户信息
//		MyUser wheremu = new MyUser();
//		wheremu.setUname("张");
//		wheremu.setUsex("男");
//		List<MyUser> listByWhere = userDao.selectUserByWhere(wheremu);
//		System.out.println("where元素================");
//		for (MyUser myUser : listByWhere) {
//			System.out.println(myUser);
//		}
//
//
//		//使用set元素修改一个用户
//		MyUser setmu = new MyUser();
//		setmu.setUid(70);
//		setmu.setUname("张七");
////		setmu.setUsex("男");
//
//		int setup = userDao.updateUserBySet(setmu);
//		System.out.println("set元素修改了" + setup + "条记录");
//		System.out.println( "================");

//
		//使用foreach元素，查询用户信息
		List<Integer> listId = new ArrayList<Integer>();
		listId.add(1);
		listId.add(71);
		listId.add(72);

		List<MyUser> listByForeach = userDao.selectUserByForeach(listId);
		System.out.println("foreach元素================");
		for (MyUser myUser : listByForeach) {
			System.out.println(myUser);
		}





//		//使用bind元素查询用户信息
//		MyUser bindmu = new MyUser();
//		bindmu.setUname("张");
//		List<MyUser> listByBind = userDao.selectUserByBind(bindmu);
//		System.out.println("bind元素================");
//		for (MyUser myUser : listByBind) {
//			System.out.println(myUser);
//		}
	}


	public void testInsert(){
		Map<String, Map<String,Object>> param = new HashMap<String,Map<String,Object>>();

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("uid", null);
		map.put("uname", "zs1");
		map.put("usex", "女");

		param.put("keys", map);

		int count = userDao.insertUser(param);
		System.out.println("testInsert()成功插入条数："+count);
	}


	public void testInserts(){
		Map<String,List<MyUser>> param = new HashMap<String,List<MyUser>>();
		List<MyUser> userLists = new ArrayList<MyUser>();
		MyUser u1 = new MyUser();
		u1.setUname("lisi");
		u1.setUsex("男");

		MyUser u2 = new MyUser();
		u2.setUname("wangwu");
		u2.setUsex("男");

		userLists.add(u1);
		userLists.add(u2);
		param.put("keys", userLists);

		int count = userDao.insertUsers(param);

		System.out.println("testInserts()成功插入条数："+count);
	}
}
