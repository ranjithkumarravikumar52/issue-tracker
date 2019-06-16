package issuetracker.controller;

import issuetracker.entity.Issue;
import issuetracker.entity.User;
import issuetracker.service.IssueService;
import issuetracker.service.UserService;
import issuetracker.service.springdatajpa.IssueServiceSDJPAImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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
    @MockBean
    private UserService userService;

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
                .title("blocker issue")
                .issueDescription("A day before release and we got this now?")
                .openedBy(johnDoe)
                .closedBy(johnDoe)
                .build();

        graphicsIssue = Issue.builder()
                .title("graphics issue ")
                .issueDescription("Bad rendering for home page")
                .openedBy(johnDoe)
                .closedBy(johnDoe)
                .build();
    }

    @Test
    public void getIssuesList() throws Exception {


        //prep
        List<Issue> issueList = new ArrayList<>();
        issueList.add(blockerIssue);
        issueList.add(graphicsIssue);

        Page<Issue> issues = new PageImpl<>(issueList, PageRequest.of(0, 10), issueList.size());

        //when
        when(issueService.findPaginated(PageRequest.of(0, 10))).thenReturn(issues);

        //assertion
        mockMvc.perform(get("/issues/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("issues"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(model().attribute("pageNumbers", hasSize(1)))
                .andExpect(view().name("issues/home"));

        //verify
        verify(issueService, times(0)).findAll();
        verify(issueService, times(1)).findPaginated(PageRequest.of(0, 10));

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

        mockMvc.perform(get("/issues/edit/" + blockerIssue.getId()))
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
        doNothing().when(mock)
                .deleteById(blockerIssue.getId());

        mock.deleteById(blockerIssue.getId());
        verify(mock, times(1)).deleteById(blockerIssue.getId());

        mockMvc.perform(get("/issues/delete/" + blockerIssue.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/issues/list"));
    }

    @Test
    public void showInDetailForm() throws Exception {
        when(issueService.findById(anyInt())).thenReturn(blockerIssue);

        mockMvc.perform(get("/issues/" + blockerIssue.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("issue", hasProperty("id", is(blockerIssue.getId()))))
                .andExpect(view().name("issues/inDetail"));

        verify(issueService).findById(anyInt());
    }
}