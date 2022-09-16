package com.controller;

import com.dao.ProductDao;
import com.po.Orders;
import com.po.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.dao.OrdersDao;

import java.util.List;

@Controller("moreToMoreController")
public class MoreToMoreController {
	@Autowired
	private OrdersDao ordersDao;

//	@Autowired
//	private ProductDao productDao;

	public void test() {
		List<Orders> os = ordersDao.selectallOrdersAndProducts();
		for (Orders orders : os) {
			System.out.println(orders);
		}
//		Product p1 = productDao.selectProductById(1);
//		System.out.println(p1);

	}
}
