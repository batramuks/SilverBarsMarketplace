package test.sliverbars.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sliverbars.api.SliverBarsApiController;
import sliverbars.api.service.SliverBarsService;
import sliverbars.model.OrderRequest;

@RunWith(SpringJUnit4ClassRunner.class)
public class SliverBarsApiControllerTest {

	
	private static final String SELL = "SELL";

	@Mock
	private SliverBarsService sliverBarsService;

	@InjectMocks
	private SliverBarsApiController sliverBarsApiController;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(sliverBarsApiController).build();

	}

	@Test
	public void testPostOrder() throws Exception {

		Long orderId=0l;
		OrderRequest orderRequest = new OrderRequest("user1", 3.5d, 306l, SELL, null);
		String uri = "/sliverbarsmarketplace/order";

		when(sliverBarsService.postOrder(orderRequest)).thenReturn(orderId);

		String inputJson = mapToJson(orderRequest);
		mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string("0"));

		// verify(sliverBarsService).postOrder(orderRequest);

	}

	@Test
	public void testDeleteOrder() throws Exception {

		String uri = "/sliverbarsmarketplace/order/1";
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void testGetLiveBoardSummary() throws Exception {

		List<String> lst = new ArrayList<String>();
		lst.add("SELL: 3.5 kg for £306");
		lst.add("SELL: 3.5 kg for £306");
		when(sliverBarsService.getLiveBoardSummary()).thenReturn(lst);

		String uri = "/sliverbarsmarketplace/liveorderboardsummary";
		mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

		verify(sliverBarsService).getLiveBoardSummary();

	}

	@Test
	public void testGetOrderList() throws Exception {
		List<OrderRequest> orderList = new ArrayList<>();
		orderList.add(new OrderRequest("user1", 3.5d, 306l, SELL, null));
		orderList.add(new OrderRequest("user2", 1.2d, 310l, SELL, null));

		when(sliverBarsService.getOrderList()).thenReturn(orderList);
		String uri = "/sliverbarsmarketplace/order";
		mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

		verify(sliverBarsService).getOrderList();

	}

	String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	<T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
