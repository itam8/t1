package org.example.clientprocessing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clientprocessing.dto.JwtResponse;
import org.example.clientprocessing.dto.LoginDto;
import org.example.clientprocessing.dto.RegisterDto;
import org.example.clientprocessing.model.DocumentType;
import org.example.clientprocessing.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @Sql(scripts = {"/data/cleanUp.sql", "/data/insertData.sql"})
    void authenticateUser() throws Exception {
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

        assertEquals(loginDto.getLogin(), jwtUtils.getUserNameFromJwtToken(jwtResponse.getToken()));
    }

    @Test
    @Sql(scripts = {"/data/cleanUp.sql", "/data/insertData.sql"})
    void create() throws Exception {
        RegisterDto registerDto = new RegisterDto(
                "asd",
                "asd",
                "asd@mail.ru",
                "11",
                "11",
                "Ivan",
                "Ivanovich",
                "Ivanov",
                LocalDate.now(),
                DocumentType.PASSPORT,
                "112",
                "1",
                "12"
        );
        String registerDtoJson = objectMapper.writeValueAsString(registerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"/data/cleanUp.sql", "/data/insertData.sql"})
    void findById() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/clients?id=" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.documentId").value("11"));
    }
}