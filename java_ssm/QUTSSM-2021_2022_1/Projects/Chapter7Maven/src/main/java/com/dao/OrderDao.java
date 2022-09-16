package com.dao;

import com.po.Order;
import com.po.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderDao")
public interface OrderDao {
	public List<Order> selectOrderById(Integer uid);
}
