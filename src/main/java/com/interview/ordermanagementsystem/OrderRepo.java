package com.interview.ordermanagementsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.interview.ordermanagementsystem.dto.OrderDetails;


@Repository
public class OrderRepo {
	
	public Map<UUID, OrderDetails> orders = new HashMap<>();

	public Map<UUID, OrderDetails> getOrders() {
		return orders;
	}

	public void setOrders(Map<UUID, OrderDetails> orders) {
		this.orders = orders;
	}
	
	
	

}
