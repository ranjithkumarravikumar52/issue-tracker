package issuetracker.controller;

import issuetracker.entity.Role;
import issuetracker.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest extends AbstractSecuredUserControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnHomePage() throws Exception{

        User ranjithUser = User.builder()
                .email("ranjith@gmail.com")
                .password("password")
                .firstName("ranjith")
                .lastName("kumar")
                .build();
        ranjithUser.setRole(Role.builder().name("admin").build());

        //when
        when(userService.findUserByEmail(anyString())).thenReturn(ranjithUser);
        when(authenticationFacadeService.getAuthentication()).thenReturn(SecurityContextHolder.getContext().getAuthentication());

        //assert
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", ranjithUser))
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