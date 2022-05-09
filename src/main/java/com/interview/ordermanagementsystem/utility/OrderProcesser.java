package com.interview.ordermanagementsystem.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.interview.ordermanagementsystem.OrderRepo;
import com.interview.ordermanagementsystem.dto.OrderDetails;
import com.interview.ordermanagementsystem.enums.Currency;
import com.interview.ordermanagementsystem.enums.OrderType;
import com.interview.ordermanagementsystem.enums.Status;

@Component
public class OrderProcesser {

	@Autowired
	OrderRepo orderRepo;

	public void processOrder(OrderDetails order) {

		OrderType orderType = order.getOrderType();
		Currency currency = order.getCurrency();
		List<OrderDetails> fillteredOrders = null;

		switch (orderType + "-" + currency) {

		// Buy GBP then look for Buy USD(2*GBP)/Sell GBP
		// Sell GBP then look for sell USD(2*GBP)/buy GBP
		// Buy USD then look for Sell USD/Buy GBP (.5 *USD)
		// SELL USD then look for buy USD/sell GBP (.5 *USD)

		case "BUY-GBP":

			System.out.println("buy-gbp");

			fillteredOrders = orderRepo.getOrders().values().stream()
					.filter(o -> ((o.getOrderType().equals(OrderType.BUY) && o.getCurrency().equals(Currency.USD)
							&& o.getAmount() <= 2 * order.getAmount())
							|| (o.getOrderType().equals(OrderType.SELL) && o.getCurrency().equals(Currency.GBP)
									&& o.getAmount() <= order.getAmount()))
							&& (!o.getStatus().equals(Status.FULLY_MATCHED)))
					.sorted(Comparator.comparingDouble(OrderDetails::getAmount)).collect(Collectors.toList());

			break;

		case "SELL-GBP":
			System.out.println("sell-gbp");
			fillteredOrders = orderRepo.getOrders().values().stream()
					.filter(o -> ((o.getOrderType().equals(OrderType.SELL) && o.getCurrency().equals(Currency.USD)
							&& o.getAmount() <= 2 * order.getAmount())
							|| (o.getOrderType().equals(OrderType.BUY) && o.getCurrency().equals(Currency.GBP)
									&& o.getAmount() <= order.getAmount()))
							&& (!o.getStatus().equals(Status.FULLY_MATCHED)))
					.sorted(Comparator.comparingDouble(OrderDetails::getAmount)).collect(Collectors.toList());
			break;
		case "BUY-USD":
			System.out.println("buy-usd");
			fillteredOrders = orderRepo.getOrders().values().stream()
					.filter(o -> ((o.getOrderType().equals(OrderType.SELL) && o.getCurrency().equals(Currency.USD)
							&& o.getAmount() <= order.getAmount())
							|| (o.getOrderType().equals(OrderType.BUY) && o.getCurrency().equals(Currency.GBP)
									&& o.getAmount() <= 0.5 * order.getAmount()))
							&& (!o.getStatus().equals(Status.FULLY_MATCHED)))
					.sorted(Comparator.comparingDouble(OrderDetails::getAmount)).collect(Collectors.toList());
			break;
		case "SELL-USD":
			System.out.println("sell-usd");
			fillteredOrders = orderRepo.getOrders().values().stream()
					.filter(o -> ((o.getOrderType().equals(OrderType.BUY) && o.getCurrency().equals(Currency.USD)
							&& o.getAmount() <= order.getAmount())
							|| (o.getOrderType().equals(OrderType.SELL) && o.getCurrency().equals(Currency.GBP)
									&& o.getAmount() <= 0.5 * order.getAmount()))
							&& (!o.getStatus().equals(Status.FULLY_MATCHED)))
					.sorted(Comparator.comparingDouble(OrderDetails::getAmount)).collect(Collectors.toList());
			break;
		}

		if (fillteredOrders == null || fillteredOrders.size() == 0) {
			order.setStatus(Status.PENDING);
		} else {
			List<UUID> matchedIds = checkForMatches(fillteredOrders, order);

			if (matchedIds.size() == 0) {
				order.setStatus(Status.PARTIALLY_MATCHED);
			} else {

				order.setStatus(Status.FULLY_MATCHED);
				order.setOrderCompletionHirarchy(matchedIds);

				for (UUID uuid : matchedIds) {
					orderRepo.getOrders().get(uuid).setStatus(Status.FULLY_MATCHED);
					orderRepo.getOrders().get(uuid).getOrderCompletionHirarchy().add(order.getUuid());
				}

			}

		}

	}

	public static List<UUID> checkForMatches(List<OrderDetails> filteredRecords, OrderDetails order) {

		List<UUID> orderIds = new ArrayList<UUID>();
		int l = 0, r = filteredRecords.size() - 1;
		Currency desiredCurrency = order.getCurrency();
		double amount1;
		double amount2;

		while (l <= r) {

			amount1 = getMoneyInDesiredCurrencyFromOrder(filteredRecords.get(l), desiredCurrency);
			amount2 = getMoneyInDesiredCurrencyFromOrder(filteredRecords.get(r), desiredCurrency);

			if (l == r) {
				if (amount1 == order.getAmount()) {
					orderIds.add(filteredRecords.get(l).getUuid());
				}
				return orderIds;
			}

			if (amount1 == order.getAmount()) {
				orderIds.add(filteredRecords.get(l).getUuid());
				return orderIds;
			}
			if (amount2 == order.getAmount()) {
				orderIds.add(filteredRecords.get(r).getUuid());
				return orderIds;
			}

			if (amount1 + amount2 == order.getAmount()) {
				orderIds.add(filteredRecords.get(l).getUuid());
				orderIds.add(filteredRecords.get(r).getUuid());
				return orderIds;
			}

			if (amount1 + amount2 < order.getAmount()) {
				l++;
			} else {
				r--;
			}

		}

		return orderIds;
	}

	public static Double getMoneyInDesiredCurrencyFromOrder(OrderDetails order, Currency currency) {

		if (order.getCurrency().equals(currency)) {
			return order.getAmount();
		}

		if (order.getCurrency().equals(Currency.USD)) {
			return order.getAmount() / 2;
		} else {
			return order.getAmount() * 2;
		}

	}

}
