package demo.resilient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Not using @WebMvcTest because it would ignore AOP
@SpringBootTest
@AutoConfigureMockMvc
class ResilientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFallback() throws Exception {
        mockMvc.perform(get("/fallback"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Fallback for")));
    }
}
