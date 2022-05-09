package com.interview.ordermanagementsystem;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.ordermanagementsystem.dto.OrderDetails;
import com.interview.ordermanagementsystem.dto.OrderRequestModel;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/placeOrder")
	public OrderDetails placeOrder(@RequestBody OrderRequestModel orderRequest) {
		
		OrderDetails order = orderService.saveOrder(orderRequest);
		return order;
		
	}
	
	@GetMapping("/getAllOrders")
	public List<OrderDetails> findAllOrders() {
		
		List<OrderDetails> orders = orderService.getAllOrders();
		return orders;
		
	}
	
	@GetMapping("/getOrder/{id}")
	public OrderDetails findAllOrders(@PathVariable("id") UUID uuid) {
		
		OrderDetails order = orderService.getOrderByID(uuid);
		return order;
		
	}
	

}
