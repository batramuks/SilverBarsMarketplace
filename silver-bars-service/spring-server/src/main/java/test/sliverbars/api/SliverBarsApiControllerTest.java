package test.sliverbars.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import sliverbars.api.SliverBarsApiController;
import sliverbars.api.service.SliverBarsService;
import sliverbars.api.service.impl.SliverBarsServiceImpl;
import sliverbars.model.OrderRequest;

public class SliverBarsApiControllerTest extends AbstractTest {

	private static final String BUY = "BUY";
	private static final String SELL = "SELL";

	@Override
	@Before
	public void setUp() {
		super.setUp();
		setMockOrderData();
	}

	@InjectMocks
	SliverBarsApiController sliverBarsApiController;

	@Mock
	SliverBarsService sliverBarsService;

	@Test
	public void testPostOrder() throws Exception {

		String uri = "/sliverbarsmarketplace/order";
		OrderRequest orderRequest = new OrderRequest("user1", 3.5d, 306l, SELL, null);

		String inputJson = super.mapToJson(orderRequest);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();

		assertEquals("1", content);

	}

	@Test
	public void testDeleteOrder() throws Exception {
		String uri = "/sliverbarsmarketplace/order/1";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void testGetLiveBoardSummary() throws Exception {

		String uri = "/sliverbarsmarketplace/liveorderboardsummary";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		String[] strlist = super.mapFromJson(content, String[].class);
		assertTrue(strlist.length > 0);

	}

	@Test
	public void testGetOrderList() throws Exception {

		String uri = "/sliverbarsmarketplace/order";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		OrderRequest[] orderList = super.mapFromJson(content, OrderRequest[].class);
		assertTrue(orderList.length > 0);
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
