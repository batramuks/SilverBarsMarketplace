package sliverbars.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import sliverbars.api.service.SliverBarsService;
import sliverbars.model.OrderRequest;

@Controller
public class SliverBarsApiController implements SliverBarsApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(SliverBarsApiController.class);

	@Autowired
	public SliverBarsService sliverBarsService;

	@Override
	public ResponseEntity<?> postOrder(@RequestBody OrderRequest orderRequest) {
		LOGGER.info("Class: SliverBarsApiController ::  postOrder()");
		Long orderId = sliverBarsService.postOrder(orderRequest);
		return new ResponseEntity<Long>(orderId, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long orderId) {
		LOGGER.info("Class: SliverBarsApiController ::  deleteOrder()");
		sliverBarsService.deleteOrder(orderId);
		return new ResponseEntity<Long>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<String>> getLiveBoardSummary() {

		LOGGER.info("Class: SliverBarsApiController ::  getLiveBoardSummary()");
		List<String> orderSummary = sliverBarsService.getLiveBoardSummary();
		return new ResponseEntity<List<String>>(orderSummary, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrderRequest>> getOrderList() {
		LOGGER.info("Class: SliverBarsApiController ::  getOrderList()");
		List<OrderRequest> orderList = sliverBarsService.getOrderList();
		return new ResponseEntity<List<OrderRequest>>(orderList, HttpStatus.OK);
	}

}
