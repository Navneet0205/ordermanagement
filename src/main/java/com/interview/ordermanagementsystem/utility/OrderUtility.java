package com.interview.ordermanagementsystem.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.interview.ordermanagementsystem.dto.OrderRequestModel;
import com.interview.ordermanagementsystem.dto.OrderDetails;

public class OrderUtility {
	
	
	public static OrderDetails orderDetailsMapper(OrderRequestModel reqModel) {
		
		OrderDetails order = new OrderDetails();
		order.setAmount(reqModel.getAmount());
		order.setCurrency(reqModel.getCurrency());
		order.setDate(new Date());
		order.setOrderCompletionHirarchy(new ArrayList<UUID>());
		order.setOrderType(reqModel.getOrderType());
		
		return order;
		
	}
	

}
