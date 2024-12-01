package com.example.onlineShop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OnlineShopApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
	void contextLoads() {
	}

    @Test
    public void createOrder() throws Exception {
        String order = """
                {
                    "customerId": 0,
                    "productIds": [],
                    "shippingAddress": ""
                }""";

        mockMvc.perform(post("/api/orders")
                        .content(order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getOrderById() throws Exception {
        mockMvc.perform(get("/api/orders/{0}", "0"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
