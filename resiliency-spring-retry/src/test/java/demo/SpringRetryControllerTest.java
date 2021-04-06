package demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SpringRetryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFallback() throws Exception {
        mockMvc.perform(get("/retry"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Try later: 5")));
    }
}
