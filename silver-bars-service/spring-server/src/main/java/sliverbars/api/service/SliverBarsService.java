package sliverbars.api.service;

import java.util.List;

import sliverbars.model.OrderRequest;

/**
 * @author batra.m
 *
 */
public interface SliverBarsService {
	/***
	 * This method is used to register an order
	 * @param orderRequest
	 * @return
	 */
	public Long postOrder(OrderRequest orderRequest);

	/***
	 * This method is used to delete or Cancel the registered by orderId
	 * @param orderId
	 */
	public void deleteOrder(Long orderId);

	/***
	 * This method will display the summary information of Live Orders.
	 * @return
	 */
	public List<String> getLiveBoardSummary();

	/***
	 * This method will show the list of orders
	 * @return 
	 */
	public List<OrderRequest> getOrderList();

}
