package it.sara.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.sara.demo.service.user.model.UserRole;
import it.sara.demo.web.auth.request.RegisterRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
      @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_withValidInput_returnsSuccess() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Mario");
        request.setLastName("Rossi");
        request.setEmail("mario.rossi@example.com");
        request.setPhoneNumber("3913618257");
        request.setPassword("password123");
        request.setRole(UserRole.USER);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value(200));
    }

    @Test
    void register_withMissingFields_returnsValidationError() throws Exception {
        RegisterRequest request = new RegisterRequest();
        // Missing all fields

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void register_withDuplicateEmail_returnsUserAlreadyExists() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Mario");
        request.setLastName("Rossi");
        request.setEmail("user0@example.com"); // Already in FakeDatabase
        request.setPhoneNumber("3913618257");
        request.setPassword("password123");
        request.setRole(UserRole.USER);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500004));
    }
}
