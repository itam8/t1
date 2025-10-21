package org.example.clientprocessing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clientprocessing.dto.ClientCardDto;
import org.example.clientprocessing.dto.ClientProductDto;
import org.example.clientprocessing.dto.ProductRegistryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void sendClientProductMessage() throws Exception {
        ClientProductDto clientProductDto = new ClientProductDto(1L, 1L);
        String clientProductDtoJson = objectMapper.writeValueAsString(clientProductDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/client-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientProductDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void sendClientCreditProductMessage() throws Exception {
        ProductRegistryDto productRegistryDto = new ProductRegistryDto(1L, 1L, 1L, 20.0, 12, 0.0);
        String productRegistryDtoJson = objectMapper.writeValueAsString(productRegistryDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/client-products/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRegistryDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void sendClientCardMessage() throws Exception {
        ClientCardDto clientCardDto = new ClientCardDto(1L, 1L, "Visa");
        String clientCardDtoJson = objectMapper.writeValueAsString(clientCardDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/client-products/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientCardDtoJson))
                .andExpect(status().isOk());
    }
}