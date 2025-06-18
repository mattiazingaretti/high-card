package it.sara.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.test.context.support.WithMockUser;

import it.sara.demo.web.user.request.GetUsersRequest;
import it.sara.demo.web.user.request.GetUsersRequest.OrderType;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUsers_withValidInput_returnsPagedUsers() throws Exception {
        GetUsersRequest request = new GetUsersRequest();
        request.setQuery("user");
        request.setOffset(0);
        request.setLimit(5);
        request.setOrder(OrderType.BY_FIRSTNAME);

        mockMvc.perform(get("/user/user")
                .contentType(MediaType.APPLICATION_JSON)
                .param("query", request.getQuery())
                .param("offset", String.valueOf(request.getOffset()))
                .param("limit", String.valueOf(request.getLimit()))
                .param("order", request.getOrder().name())
                .header("API-Version", "1.0.0")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUsers_withInvalidInput_returnsValidationError() throws Exception {
        GetUsersRequest request = new GetUsersRequest();
        request.setQuery(null); 
        request.setOffset(-1);  
        request.setLimit(0);    
        request.setOrder(null); 

        mockMvc.perform(get("/user/user")
                .contentType(MediaType.APPLICATION_JSON)
                .param("query", "")
                .param("offset", String.valueOf(request.getOffset()))
                .param("limit", String.valueOf(request.getLimit()))
                // .param("order", "") // missing
                .header("API-Version", "1.0.0")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400));
    }
}
