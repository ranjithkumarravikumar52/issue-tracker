package issuetracker.controller;

import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import javax.sql.DataSource;


@WithMockUser
public class AbstractSecuredUserControllerTest {
    @MockBean
    protected DataSource dataSource;

    @MockBean
    protected UserService userService;

    @MockBean
    protected IssueService issueService;

    @MockBean
    protected RoleService roleService;

    @MockBean
    protected ProjectService projectService;
}
