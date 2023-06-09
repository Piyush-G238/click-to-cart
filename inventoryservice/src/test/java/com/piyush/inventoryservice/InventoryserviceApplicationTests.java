package com.piyush.inventoryservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class InventoryserviceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_createInventory() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJSON())
                ).andExpect(MockMvcResultMatchers
                        .status()
                        .isCreated());
    }

    @Test
    public void test_getAllInventory() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/inventories")
                ).andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].quantity")
                        .value(3L));
    }

    @Test
    public void test_deleteInventory() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/v1/inventories/{id}", 1L)
                ).andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    @Test
    public void test_updateInventoryByProductId() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/api/v1/inventories/{productId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJSON())
                ).andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    private String toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Long> req = new HashMap<>();
        req.put("qty", 4L);
/*		InventoryDto dto= InventoryDto.builder()
				.productId(1L)
				.name("product1_inventory")
				.quantity(3L)
				.build();
*/
        return mapper.writeValueAsString(req);
    }
}
