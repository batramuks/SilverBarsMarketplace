package sliverbars.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sliverbars.api.exception.OrderNotFoundException;
import sliverbars.api.service.SliverBarsService;
import sliverbars.model.OrderRequest;

/**
 * @author batra.m
 *
 */
@Service
public class SliverBarsServiceImpl implements SliverBarsService {

	private static final String BUY = "BUY";
	private static final String SELL = "SELL";
	private static final Logger LOGGER = LoggerFactory.getLogger(SliverBarsServiceImpl.class);
	public static Map<Long, OrderRequest> orderMap = new HashMap<Long, OrderRequest>();
	Long orderId = 0L;
   
	public Long postOrder(OrderRequest orderRequest) {
		LOGGER.info("Class: SliverBarsServiceImpl ::  registerOrderPost()");
		LOGGER.info("OrderType: " + orderRequest.getOrderType() + "OrderQuantity: " + orderRequest.getOrderQuantity()
				+ "Price: " + orderRequest.getPrice() + "UserId: " + orderRequest.getUserId());
	
		orderId++;
		orderRequest.setOrderId(orderId);
		orderMap.put(orderId, orderRequest);
		//setMockOrderData(); Enable it to test
	  	return orderId;
	}

	@Override
	public void deleteOrder(Long orderId) {
		LOGGER.info("Class: SliverBarsServiceImpl ::  orderDelete()");
		if (null == orderMap.get(orderId)) {
			throw new OrderNotFoundException(orderId);
		}
		orderMap.remove(orderId);

	}

	@Override
	public List<String> getLiveBoardSummary() {
		LOGGER.info("Class: SliverBarsServiceImpl ::  getLiveBoardSummary()");
		if (orderMap.size()==0) {
			throw new OrderNotFoundException(0l);
		}
		List<OrderRequest> orderList = orderMap.values().stream().collect(Collectors.toList());

		/*** Create SELL List ***/
		List<OrderRequest> sellList = orderList.stream().filter(i -> i.getOrderType().equalsIgnoreCase(SELL) == true)
				.collect(Collectors.toList());
		/*** Create BUY List ***/
		List<OrderRequest> buyList = orderList.stream().filter(i -> i.getOrderType().equalsIgnoreCase(BUY) == true)
				.collect(Collectors.toList());
		/*** Group by Price ***/
		Map<Long, Double> sellMap = sellList.stream().collect(Collectors.groupingBy(OrderRequest::getPrice,
				Collectors.summingDouble(OrderRequest::getOrderQuantity)));
		/*** Group by Price ***/
		Map<Long, Double> buyMap = buyList.stream().collect(Collectors.groupingBy(OrderRequest::getPrice,
				Collectors.summingDouble(OrderRequest::getOrderQuantity)));
		/*** Sort by Price Asc***/
		Map<Long,Double> sellSortedMap = sellMap.entrySet().stream().sorted(Map.Entry.<Long, Double>comparingByKey()).collect(
				Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e1, LinkedHashMap::new));
		/*** Sort by Price Desc***/
		Map<Long,Double> buySortedMap = buyMap.entrySet().stream().sorted(Map.Entry.<Long, Double>comparingByKey().reversed()).collect(
				Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e1, LinkedHashMap::new));
					
		List<String> liveOrderSummary = new ArrayList<>();
		sellSortedMap.forEach((k, v) -> {
			liveOrderSummary.add("SELL: "+ v + " kg for £" + k);
		});
		
		buySortedMap.forEach((k, v) -> {
			liveOrderSummary.add("BUY: "+v + " kg for £" + k);
		});

		return liveOrderSummary;
	}

	@Override
	public List<OrderRequest> getOrderList() {
		LOGGER.info("Class: SliverBarsServiceImpl ::  getOrderList()");
		
		if (orderMap.size()==0) {
			throw new OrderNotFoundException(0l);
		}
		List<OrderRequest> orderList = orderMap.values().stream().collect(Collectors.toList());
		return orderList;
	}

	public void setMockOrderData() {
		orderMap.put(1l, new OrderRequest("user1", 3.5d, 306l, SELL, null));
		orderMap.put(2l, new OrderRequest("user2", 1.2d, 310l, SELL, null));
		orderMap.put(3l, new OrderRequest("user3", 1.5d, 307l, SELL, null));
		orderMap.put(4l, new OrderRequest("user4", 2.0d, 306l, SELL, null));
		orderMap.put(5l, new OrderRequest("user1", 3.5d, 306l, BUY, null));
		orderMap.put(6l, new OrderRequest("user2", 1.2d, 310l, BUY, null));
		orderMap.put(7l, new OrderRequest("user3", 1.5d, 307l, BUY, null));
		orderMap.put(8l, new OrderRequest("user4", 2.0d, 306l, BUY, null));
	}
}
