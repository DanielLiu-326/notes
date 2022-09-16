package com.po;

import java.util.List;

/**
 *springtest数据库中orders表的持久化类
 */
public class Order {
	private Integer id;
	private  String ordersn;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrdersn() {
		return ordersn;
	}
	public void setOrdersn(String ordersn) {
		this.ordersn = ordersn;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ",ordersn=" + ordersn+ "]";
	}
}
