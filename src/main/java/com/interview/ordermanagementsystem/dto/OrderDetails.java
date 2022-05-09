package com.interview.ordermanagementsystem.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.interview.ordermanagementsystem.enums.Currency;
import com.interview.ordermanagementsystem.enums.OrderType;
import com.interview.ordermanagementsystem.enums.Status;


public class OrderDetails {
	
	private UUID uuid;
	private OrderType orderType;
	private Currency currency;
	private double amount;
	private Status status;
	private Date date;
	private List<UUID> orderMatchingBreakdown;
	
	public OrderDetails() {
		this.uuid = UUID.randomUUID();	
				
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<UUID> getOrderCompletionHirarchy() {
		return orderMatchingBreakdown;
	}

	public void setOrderCompletionHirarchy(List<UUID> orderCompletionHirarchy) {
		this.orderMatchingBreakdown = orderCompletionHirarchy;
	}

	public UUID getUuid() {
		return uuid;
	}

	@Override
	public String toString() {
		return "OrderDetails [uuid=" + uuid + ", orderType=" + orderType + ", currency=" + currency + ", amount="
				+ amount + ", status=" + status + ", date=" + date + ", orderCompletionHirarchy="
				+ orderMatchingBreakdown + "]";
	}
	
	
	
	

}
