package ru.annikonenkov;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TryStringbootApplicationTests {

    @Autowired
    private MockMvc mvc;
    
    @Test
    void getHelloFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/forTry/get?info=Hello").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(not("This is the GET request. It has next requestParameter with info = Helloddd")));

    }
    
    @Test
    void getHelloSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/forTry/get?info=Hello").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(equalTo("This is the GET request. It has next requestParameter with info = Hello")));

    }

}
