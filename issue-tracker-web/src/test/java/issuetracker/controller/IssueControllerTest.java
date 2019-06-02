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

    @Before
    public void setup() {
        User johnDoe = User.builder()
                .userName("johnDoe")
                .password("pass123")
                .email("johnDoe@gmail.com")
                .firstName("john")
                .lastName("doe")
                .build();

        User janeDoe = User.builder()
                .userName("janeDoe")
                .password("pass456")
                .email("janeDoe@gmail.com")
                .firstName("jane")
                .lastName("doe")
                .build();

        blockerIssue = Issue.builder()
                .issueDescription("blocker issue")
                .postedBy(johnDoe)
                .openedBy(johnDoe)
                .fixedBy(johnDoe)
                .closedBy(johnDoe)
                .build();

        graphicsIssue = Issue.builder()
                .issueDescription("graphics issue")
                .postedBy(janeDoe)
                .openedBy(johnDoe)
                .fixedBy(janeDoe)
                .closedBy(johnDoe)
                .build();
    }

    @Test
    public void getIssuesList() throws Exception {
        //prep
        Set<Issue> issues = new HashSet<>();
        issues.add(blockerIssue);
        issues.add(graphicsIssue);

        //when
        when(issueService.findAll()).thenReturn(issues);

        //assertion
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
                .andExpect(view().name("redirect:/issues/list"));

        verify(issueService).save(ArgumentMatchers.any());
    }

    @Test
    public void showUpdateForm() throws Exception {
        when(issueService.findById(anyInt())).thenReturn(blockerIssue);

        mockMvc.perform(get("/issues/edit/"+blockerIssue.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("issue", hasProperty("id", is(blockerIssue.getId()))))
                .andExpect(view().name("issues/showAddOrUpdate"));

        verify(issueService).findById(anyInt());
    }

    /* *
     * Mocking void methods is a bit different. In below code we created a mock of the {@link IssueServiceSDJPAImpl}<br>
     * particularly deleteById().
     * And then we verify that method is called exactly once.*/

    @Test
    public void deleteIssue() throws Exception {
        IssueServiceSDJPAImpl mock = mock(IssueServiceSDJPAImpl.class);
        doNothing().when(mock).deleteById(blockerIssue.getId());

        mock.deleteById(blockerIssue.getId());
        verify(mock, times(1)).deleteById(blockerIssue.getId());

        mockMvc.perform(get("/issues/delete/"+blockerIssue.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/issues/list"));
    }
}