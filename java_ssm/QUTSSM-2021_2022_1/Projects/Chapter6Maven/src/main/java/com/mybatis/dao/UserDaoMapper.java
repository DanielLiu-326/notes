package com.mybatis.dao;

import com.mybatis.po.MyUser;

import java.util.List;

public interface UserDaoMapper {
    /**
     * 接口方法对应SQL映射文件UserMapper.xml中的id
     */
    public MyUser selectUserById(Integer uid);
    public List<MyUser> selectAllUser();
    public int addUser(MyUser user);
    public int updateUser(MyUser user);
    public int deleteUser(Integer uid);
}
