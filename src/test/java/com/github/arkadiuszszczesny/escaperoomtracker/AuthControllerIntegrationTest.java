package com.github.arkadiuszszczesny.escaperoomtracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIntegrationTest {



    @Autowired
    private MockMvc mockMvc;

    @Test
    void register_shouldReturn201_whenDataIsValid() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "integrationuser",
                            "email": "integration@test.com",
                            "password": "password123"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.username").value("integrationuser"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    void getVoivodeships_shouldReturn200_withoutToken() throws Exception {
        mockMvc.perform(get("/api/voivodeships"))
                .andExpect(status().isOk());
    }

    @Test
    void getMyVisits_shouldReturn401_withoutToken() throws Exception {
        mockMvc.perform(get("/api/visits/my"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createRoom_shouldReturn403_forRoleUser() throws Exception {

        String response = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "roleuser",
                            "email": "roleuser@test.com",
                            "password": "password123"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = response.split("\"token\":\"")[1].split("\"")[0];

        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content("""
                        {
                            "name": "Test Room",
                            "difficulty": 3,
                            "durationMinutes": 60,
                            "branchId": "00000000-0000-0000-0000-000000000000"
                        }
                        """))
                .andExpect(status().isForbidden());
    }
}
