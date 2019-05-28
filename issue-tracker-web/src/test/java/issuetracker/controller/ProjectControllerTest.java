package issuetracker.controller;

import issuetracker.entity.Project;
import issuetracker.entity.User;
import issuetracker.service.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private Project project1;

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

        project1 = Project.builder().id(1).projectDescription("Project 1 Description").userList(Arrays.asList(johnDoe, janeDoe)).build();
    }

    @Test
    public void listProjects() throws Exception{
        HashSet<Project> projects = new HashSet<>();
        projects.add(project1);
        when(projectService.findAll()).thenReturn(projects);

        mockMvc.perform(get("/projects/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("projects"))
                .andExpect(model().attribute("projects", hasSize(1)))
                .andExpect(view().name("projects/home"));

        verify(projectService, times(1)).findAll();
    }

    @Test
    public void showProjectAddForm() throws Exception {
        mockMvc.perform(get("/projects/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("project"))
                .andExpect(view().name("projects/showAddOrUpdate"));

        verifyZeroInteractions(projectService);
    }

    @Test
    public void addProject() throws Exception{
        when(projectService.save(ArgumentMatchers.any(Project.class))).thenReturn(project1);

        mockMvc.perform(post("/projects/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects/list"));

        verify(projectService, times(1)).save(ArgumentMatchers.any(Project.class));
    }

    @Test
    public void showUpdateForm() throws Exception{
        when(projectService.findById(anyInt())).thenReturn(project1);

        mockMvc.perform(get("/projects/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("project", hasProperty("id", is(1))))
                .andExpect(model().attribute("project", hasProperty("projectDescription", equalTo("Project 1 Description"))))
                .andExpect(view().name("projects/showAddOrUpdate"));

        verify(projectService, times(1)).findById(anyInt());
    }

    @Test
    public void deleteProject() throws Exception{
        mockMvc.perform(delete("/projects/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects/list"));
    }
}