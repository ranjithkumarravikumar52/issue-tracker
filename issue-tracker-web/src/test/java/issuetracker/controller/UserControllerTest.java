package issuetracker.controller;

import issuetracker.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest extends AbstractSecuredUserControllerTest{

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
        List<User> userList = new ArrayList<>(this.userSet);
        Page<User> users = new PageImpl<>(userList, PageRequest.of(0, 10), this.userSet.size());

        when(userService.findPaginated(PageRequest.of(0, 10))).thenReturn(users);

        mockMvc.perform(get("/users/list/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(model().attribute("pageNumbers", hasSize(1)))
                .andExpect(view().name("users/home"));

        verify(userService, times(0)).findAll();
        verify(userService, times(1)).findPaginated(PageRequest.of(0, 10));

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
        mockMvc.perform(get("/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"));

        verify(userService, times(1)).deleteById(1);

    }

    @Test
    public void showInDetailForm() throws Exception {
        when(userService.findById(anyInt())).thenReturn(johnDoe);

        mockMvc.perform(get("/users/" + johnDoe.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", hasProperty("id", is(johnDoe.getId()))))
                .andExpect(view().name("users/inDetail"));

        verify(userService, times(1)).findById(anyInt());
    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", ("User profile " +
                "pic").getBytes());

        mockMvc.perform(multipart("/users/image/1").file(imageFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/1"));

        verify(userService, times(1)).saveImageFile(anyInt(), any());
    }

    @Test
    public void renderImageFromDB() throws Exception {
        //given
        String s = "fake image text";
        Byte[] byteBoxed = new Byte[s.getBytes().length];

        int i = 0;
        for (byte b : s.getBytes()) {
            byteBoxed[i++] = b;
        }

        User jimmyDoe = User.builder()
                .id(3)
                .image(byteBoxed)
                .build();


        when(userService.findById(anyInt())).thenReturn(jimmyDoe);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/users/image/3"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, responseBytes.length);
    }

}