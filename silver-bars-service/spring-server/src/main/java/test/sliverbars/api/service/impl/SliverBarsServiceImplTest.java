package test.sliverbars.api.service.impl;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import sliverbars.api.exception.OrderNotFoundException;
import sliverbars.api.service.SliverBarsService;
import sliverbars.api.service.impl.SliverBarsServiceImpl;
import sliverbars.model.OrderRequest;

/**
 * @author batra.m
 *
 */
@SpringBootTest
public class SliverBarsServiceImplTest {

	private SliverBarsService sliverBarsService = new SliverBarsServiceImpl();

	private static final String BUY = "BUY";
	private static final String SELL = "SELL";

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		setMockOrderData();

	}

	@Test
	public void testPostOrder() {
		OrderRequest orderRequest = new OrderRequest("user5", 4d, 305l, SELL, null);
		Long orderId = sliverBarsService.postOrder(orderRequest);
		assertEquals(1l, orderId.longValue());
	}

	@Test
	public void testDeleteOrder_Exception() {
		int dataNotFound = 0;
		try {
			sliverBarsService.deleteOrder(0l);
		} catch (OrderNotFoundException e) {
			dataNotFound = 404;
		}
		assertEquals(dataNotFound, HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void testDeleteOrder() {
		OrderRequest orderRequest = new OrderRequest("user1", 3.5d, 306l, SELL, null);
		Long orderId = sliverBarsService.postOrder(orderRequest);
		sliverBarsService.deleteOrder(orderId);

	}

	@Test
	public void testGetLiveBoardSummary() {
		List<String> lst = sliverBarsService.getLiveBoardSummary();
		Assert.assertThat(lst, hasItems("SELL: 1.2 kg for £310", "BUY: 1.5 kg for £307"));
	}

	@Test
	public void testGetOrderList() {
		List<OrderRequest> lst = sliverBarsService.getOrderList();
		assertTrue(lst.size() > 0);

	}

	public void setMockOrderData() {

		SliverBarsServiceImpl.orderMap.put(1l, new OrderRequest("user1", 3.5d, 306l, SELL, null));
		SliverBarsServiceImpl.orderMap.put(2l, new OrderRequest("user2", 1.2d, 310l, SELL, null));
		SliverBarsServiceImpl.orderMap.put(3l, new OrderRequest("user3", 1.5d, 307l, SELL, null));
		SliverBarsServiceImpl.orderMap.put(4l, new OrderRequest("user4", 2.0d, 306l, SELL, null));
		SliverBarsServiceImpl.orderMap.put(5l, new OrderRequest("user1", 3.5d, 306l, BUY, null));
		SliverBarsServiceImpl.orderMap.put(6l, new OrderRequest("user2", 1.2d, 310l, BUY, null));
		SliverBarsServiceImpl.orderMap.put(7l, new OrderRequest("user3", 1.5d, 307l, BUY, null));
		SliverBarsServiceImpl.orderMap.put(8l, new OrderRequest("user4", 2.0d, 306l, BUY, null));
	}
}
