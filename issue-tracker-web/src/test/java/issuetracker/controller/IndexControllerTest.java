package issuetracker.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest extends AbstractSecuredUserControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnHomePage() throws Exception{
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void shouldNotReturnHomePage() throws Exception{
        this.mockMvc.perform(get("/hello"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnNothingImplementedPage() throws Exception{
        this.mockMvc.perform(get("/oops"))
                .andExpect(status().isOk())
                .andExpect(view().name("nothingImplemented"));
    }

}