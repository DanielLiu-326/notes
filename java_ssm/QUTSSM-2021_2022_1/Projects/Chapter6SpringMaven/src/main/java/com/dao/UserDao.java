package com.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.po.MyUser;
//@Repository("userDao")
public interface UserDao {
	/**
	 * 接口方法对应SQL映射文件UserMapper.xml中的id
	 */
	public MyUser selectUserById(Integer uid);
	public List<MyUser> selectAllUser();
	public int addUser(MyUser user);
	public int updateUser(MyUser user);
	public int deleteUser(Integer uid);
}
