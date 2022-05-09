package com.interview.ordermanagementsystem.dto;

import com.interview.ordermanagementsystem.enums.Currency;
import com.interview.ordermanagementsystem.enums.OrderType;

public class OrderRequestModel {
	
	private OrderType orderType;
	private Currency currency;
	private double amount;
	
	public OrderRequestModel(OrderType orderType, Currency currency, double amount) {
		super();
		this.orderType = orderType;
		this.currency = currency;
		this.amount = amount;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CreateOrderRequestModel [orderType=" + orderType + ", currency=" + currency + ", amount=" + amount
				+ "]";
	}
	
	
	
	
	

}
