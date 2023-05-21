package com.piyush.orderservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piyush.orderservice.dto.OrderDto;
import com.piyush.orderservice.dto.OrderItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OrderserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test_createOrder() throws Exception {
		mockMvc
				.perform(MockMvcRequestBuilders
						.post("/api/v1/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSONObject())
				).andExpect(MockMvcResultMatchers
						.status()
						.isCreated());
	}

	private String toJSONObject() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		OrderItemDto itemDto1 = OrderItemDto
				.builder()
				.productId(1L)
				.quantity(1L)
				.build();
		OrderDto orderDto = OrderDto.builder()
				.orderItems(Set.of(itemDto1))
				.build();
		return mapper.writeValueAsString(orderDto);
	}
}
