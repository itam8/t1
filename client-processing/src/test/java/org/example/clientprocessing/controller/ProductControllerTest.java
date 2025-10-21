package org.example.clientprocessing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clientprocessing.dto.*;
import org.example.clientprocessing.model.Key;
import org.example.clientprocessing.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(2L);
        product.setName("product");
        product.setProductKey(Key.DC);
        product.setCreateDate(LocalDate.now());
    }

    String getToken() throws Exception {
        LoginDto loginDto = new LoginDto("login", "asd");
        String loginDtoJson = objectMapper.writeValueAsString(loginDto);

        String jwtResponseJson = mockMvc.perform(MockMvcRequestBuilders.post("/clients/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginDtoJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(jwtResponseJson, JwtResponse.class);

        return jwtResponse.getToken();
    }

    @Test
    @Sql(scripts = {"/data/cleanUp.sql", "/data/insertData.sql"})
    void create() throws Exception {
        String token = getToken();
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"/data/cleanUp.sql", "/data/insertData.sql"})
    void update() throws Exception {
        String token = getToken();
        product.setId(1L);
        product.setName("update");
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/products")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"/data/cleanUp.sql", "/data/insertData.sql"})
    void delete() throws Exception {
        String token = getToken();
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/products?id=" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}