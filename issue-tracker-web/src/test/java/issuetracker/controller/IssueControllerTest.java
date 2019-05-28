package issuetracker.controller;

import issuetracker.entity.Issue;
import issuetracker.entity.User;
import issuetracker.service.IssueService;
import issuetracker.service.springdatajpa.IssueServiceSDJPAImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(IssueController.class)
public class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    User user;
    Issue issue;

    @Before
    public void setup(){
        user = new User("johnDoe", "password123", "johnDoe@gmail.com", "john", "doe");
        user.setId(1);
        issue = new Issue("Fake Issue Description", user, user,user,user);
        issue.setId(1);
    }

    @Test
    public void getIssuesList() throws Exception{
        Set<Issue> issues = new HashSet<>();
        issues.add(issue);
        when(issueService.findAll()).thenReturn(issues);

        mockMvc.perform(get("/issues/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("issues"))
                .andExpect(model().attribute("issues", hasSize(1)))
                .andExpect(view().name("issues/home"));

    }

    @Test
    public void showAddForm() throws Exception{
        mockMvc.perform(get("/issues/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("issue"))
                .andExpect(view().name("issues/showAddOrUpdate"));

        verifyZeroInteractions(issueService);
    }

    @Test
    public void addIssue() throws Exception {
        when(issueService.save(ArgumentMatchers.any())).thenReturn(issue);

        mockMvc.perform(post("/issues/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/issue/list"));

        verify(issueService).save(ArgumentMatchers.any());
    }

    @Test
    public void showUpdateForm() throws Exception{
        when(issueService.findById(anyInt())).thenReturn(issue);

        mockMvc.perform(get("/issues/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("issue", hasProperty("id", is(1))))
                .andExpect(view().name("issues/showAddOrUpdate"));

        verify(issueService).findById(anyInt());
    }

    @Test
    public void deleteIssue() throws Exception {
        /**
         * Mocking void methods is a bit different. In below code we created a mock of the {@link IssueServiceSDJPAImpl}<br>
         *     particularly deleteById().
         *     And then we verify that method is called exactly once.
         */
        IssueServiceSDJPAImpl mock = mock(IssueServiceSDJPAImpl.class);
        doNothing().when(mock).deleteById(1);

        mock.deleteById(1);
        verify(mock, times(1)).deleteById(1);

        mockMvc.perform(get("/issues/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/issues/list"));
    }
}