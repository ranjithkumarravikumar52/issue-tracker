package issuetracker.controller;

import issuetracker.entity.Role;
import issuetracker.entity.User;
import issuetracker.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @MockBean
    private RoleService roleService;

    @Autowired
    private MockMvc mockMvc;

    private Role developerRole;

    @Before
    public void setup(){
        User johnDoe = User.builder()
                .id(1)
                .userName("johnDoe")
                .password("pass123")
                .email("johnDoe@gmail.com")
                .firstName("john")
                .lastName("doe")
                .build();

        User janeDoe = User.builder()
                .id(2)
                .userName("janeDoe")
                .password("pass456")
                .email("janeDoe@gmail.com")
                .firstName("jane")
                .lastName("doe")
                .build();

        developerRole = Role.builder().id(1).name("Developer").build();
    }

    @Test
    public void listRoles() throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(developerRole);
        when(roleService.findAll()).thenReturn(roles);

        mockMvc.perform(get("/roles/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attribute("roles", hasSize(1)))
                .andExpect(view().name("roles/home"));

        verify(roleService, times(1)).findAll();
    }

    @Test
    public void showRoleAddForm() throws Exception {
        mockMvc.perform(get("/roles/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("role"))
                .andExpect(view().name("roles/showAddOrUpdate"));

        verifyZeroInteractions(roleService);

    }

    @Test
    public void addRole() throws Exception{
        when(roleService.save(any(Role.class))).thenReturn(developerRole);

        mockMvc.perform(post("/roles/new").param("id", "1").param("name", "Developer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roles/list"));

        verify(roleService, times(1)).save(any(Role.class));
    }

    @Test
    public void showUpdateForm() throws Exception {

        when(roleService.findById(anyInt())).thenReturn(developerRole);

        mockMvc.perform(get("/roles/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("role"))
                .andExpect(model().attribute("role", hasProperty("id", is(1))))
                .andExpect(view().name("roles/showAddOrUpdate"));

        verify(roleService, times(1)).findById(anyInt());

    }

    @Test
    public void deleteRole() throws Exception{
        mockMvc.perform(get("/roles/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/roles/list"));
    }














}