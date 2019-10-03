package sliverbars.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sliverbars.model.OrderRequest;

/**
 * @author batra.m
 *
 */
public interface SliverBarsApi {
	/***
	 * This method is used to register an order
	 * @param orderRequest
	 * @return
	 */
	@RequestMapping(value = "/sliverbarsmarketplace/order", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<?> postOrder(@RequestBody OrderRequest orderRequest);
	/***
	 * This method is used to delete or Cancel the registered by orderId
	 * @param orderId
	 */
	@RequestMapping(value = "/sliverbarsmarketplace/order/{orderId}", produces = { "application/json" },  method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long orderId);
	/***
	 * This method will display the summary information of Live Orders.
	 * @return
	 */
	@RequestMapping(value = "/sliverbarsmarketplace/liveorderboardsummary", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<String>> getLiveBoardSummary();
	/***
	 * This method will show the list of orders
	 * @return 
	 */
	@RequestMapping(value = "/sliverbarsmarketplace/order", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<OrderRequest>> getOrderList();

}
