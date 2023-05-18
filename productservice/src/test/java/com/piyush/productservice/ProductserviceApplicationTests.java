package com.piyush.productservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piyush.productservice.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test_productCreate() throws Exception {
		mockMvc
				.perform(MockMvcRequestBuilders
						.post("/api/v1/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSON())
				).andExpect(MockMvcResultMatchers
						.status()
						.isCreated());
	}

	@Test
	public void test_getProductByName() throws Exception {
		mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/v1/products")
						.param("name","Apple Mac Book")
				).andExpect(MockMvcResultMatchers
						.jsonPath("$.description").value("One of best products by apple"));
	}
	private String toJSON() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ProductDto productDto = ProductDto.builder()
				.name("Apple Mac Book")
				.description("One of best products by apple")
				.price(88000.0).build();
		return mapper.writeValueAsString(productDto);
	}
}
