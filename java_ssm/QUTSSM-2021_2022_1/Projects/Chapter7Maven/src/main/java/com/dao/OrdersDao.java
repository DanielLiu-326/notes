package com.dao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.po.Orders;

public interface OrdersDao {
	public List<Orders> selectallOrdersAndProducts();
}
