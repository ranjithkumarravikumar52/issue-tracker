package issuetracker.controller;

import issuetracker.entity.Issue;
import issuetracker.entity.User;
import issuetracker.repository.UserRepository;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(IssueController.class)
public class IssueControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IssueService issueService;

    private Issue blockerIssue;
    private Issue graphicsIssue;

    //TODO uncomment
    /*@Before
    public void setup() {
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

        blockerIssue = Issue.builder()
                .id(1)
                .issueDescription("blocker issue")
                .postedBy(johnDoe)
                .openedBy(johnDoe)
                .fixedBy(johnDoe)
                .closedBy(johnDoe)
                .build();

        graphicsIssue = Issue.builder()
                .id(2)
                .issueDescription("graphics issue")
                .postedBy(janeDoe)
                .openedBy(johnDoe)
                .fixedBy(janeDoe)
                .closedBy(johnDoe)
                .build();
    }

    @Test
    public void getIssuesList() throws Exception {
        Set<Issue> issues = new HashSet<>();
        issues.add(blockerIssue);
        issues.add(graphicsIssue);
        when(issueService.findAll()).thenReturn(issues);

        mockMvc.perform(get("/issues/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("issues"))
                .andExpect(model().attribute("issues", hasSize(2)))
                .andExpect(view().name("issues/home"));

    }

    @Test
    public void showAddForm() throws Exception {
        mockMvc.perform(get("/issues/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("issue"))
                .andExpect(view().name("issues/showAddOrUpdate"));

        verifyZeroInteractions(issueService);
    }

    @Test
    public void addIssue() throws Exception {
        when(issueService.save(ArgumentMatchers.any())).thenReturn(blockerIssue);

        mockMvc.perform(post("/issues/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/issue/list"));

        verify(issueService).save(ArgumentMatchers.any());
    }

    @Test
    public void showUpdateForm() throws Exception {
        when(issueService.findById(anyInt())).thenReturn(blockerIssue);

        mockMvc.perform(get("/issues/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("issue", hasProperty("id", is(1))))
                .andExpect(view().name("issues/showAddOrUpdate"));

        verify(issueService).findById(anyInt());
    }

    *//**
     * Mocking void methods is a bit different. In below code we created a mock of the {@link IssueServiceSDJPAImpl}<br>
     * particularly deleteById().
     * And then we verify that method is called exactly once.
     *//*
    @Test
    public void deleteIssue() throws Exception {
        IssueServiceSDJPAImpl mock = mock(IssueServiceSDJPAImpl.class);
        doNothing().when(mock).deleteById(1);

        mock.deleteById(1);
        verify(mock, times(1)).deleteById(1);

        mockMvc.perform(get("/issues/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/issues/list"));
    }*/
}