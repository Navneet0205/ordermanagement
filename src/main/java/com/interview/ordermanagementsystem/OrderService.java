package com.interview.ordermanagementsystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.ordermanagementsystem.dto.OrderDetails;
import com.interview.ordermanagementsystem.dto.OrderRequestModel;
import com.interview.ordermanagementsystem.utility.OrderProcesser;
import com.interview.ordermanagementsystem.utility.OrderUtility;

@Service
public class OrderService {

	@Autowired
	OrderProcesser orderProcesser;

	@Autowired
	OrderRepo orderRepo;

	public OrderDetails saveOrder(OrderRequestModel requestModel) {

		OrderDetails order = OrderUtility.orderDetailsMapper(requestModel);
		orderProcesser.processOrder(order);
		orderRepo.getOrders().put(order.getUuid(), order);
		System.out.println(order);
		return order;

	}

	public OrderDetails getOrderByID(UUID uuid) {

		OrderDetails order = orderRepo.getOrders().get(uuid);
		return order;
	}

	public List<OrderDetails> getAllOrders() {

		Collection<OrderDetails> values = orderRepo.getOrders().values();
		List<OrderDetails> orders = new ArrayList<>(values);
		
		return orders;
	}

}
