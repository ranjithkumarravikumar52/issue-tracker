package issuetracker.controller;

import issuetracker.entity.User;
import issuetracker.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User johnDoe;
    private User janeDoe;
    private Set<User> userSet;

    @Before
    public void setUp() throws Exception {
        johnDoe = User.builder()
                .id(1)
                .userName("johnDoe")
                .password("pass123")
                .email("johnDoe@gmail.com")
                .firstName("john")
                .lastName("doe")
                .build();

        janeDoe = User.builder()
                .id(2)
                .userName("janeDoe")
                .password("pass456")
                .email("janeDoe@gmail.com")
                .firstName("jane")
                .lastName("doe")
                .build();

        userSet = new HashSet<>();
        userSet.add(johnDoe);
        userSet.add(janeDoe);
    }

    @Test
    public void listUsers() throws Exception {
        when(userService.findAll()).thenReturn(userSet);

        mockMvc.perform(get("/users/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(view().name("users/home"));

        verify(userService, times(1)).findAll();

    }

    @Test
    public void showAddForm() throws Exception {
        mockMvc.perform(get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("users/showAddOrUpdate"));

        verifyZeroInteractions(userService);
    }

    @Test
    public void addUserValidationSuccessful() throws Exception {
        mockMvc.perform(post("/users/new")
                .param("id", "2")
                .param("userName", "janeDoe")
                .param("password", "pass456")
                .param("email", "janeDoe@gmail.com")
                .param("firstName", "jane")
                .param("lastName", "doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"));

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    public void addUserValidationError() throws Exception {
        mockMvc.perform(post("/users/new")
                .param("id", "2")
                .param("userName", "janeDoe")
                .param("password", "")
                .param("email", "janeDoe@gmail.com")
                .param("firstName", "jane")
                .param("lastName", "doe"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/showAddOrUpdate"));

        verifyZeroInteractions(userService);
    }

    @Test
    public void showUpdateForm() throws Exception {
        when(userService.findById(anyInt())).thenReturn(johnDoe);

        mockMvc.perform(get("/users/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", hasProperty("id", is(1))))
                .andExpect(view().name("users/showAddOrUpdate"));

        verify(userService, times(1)).findById(anyInt());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"));

        verify(userService, times(1)).deleteById(1);

    }
}