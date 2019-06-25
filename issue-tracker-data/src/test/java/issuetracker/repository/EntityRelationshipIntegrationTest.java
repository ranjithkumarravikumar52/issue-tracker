package issuetracker.repository;

import issuetracker.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EntityRelationshipIntegrationTest {
    //static issue id counter
    private static int issueCounter = 1;

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public IssueRepository issueRepository;
    @Autowired
    public ProjectRepository projectRepository;
    @Autowired
    public RoleRepository roleRepository;

    //1 role
    private Role admin;

    //1 projects
    private Project mobileApp;

    //1 users
    private User adminUser;

    //2 issue
    private Issue issueOpen;
    private Issue issueClosed;

    @Before
    public void setup() {
        initProjectsData();
        initRolesData();
        createTestUserWithRoles();

        issueOpen = initOpenIssuesData(adminUser, mobileApp);
        issueClosed = initClosedIssuesData(adminUser, mobileApp);

        //save all issues at once
        issueRepository.saveAll(Arrays.asList(issueOpen, issueClosed));
        projectRepository.save(mobileApp);
    }

    private void initProjectsData() {
        mobileApp = Project.builder()
                .title("mobile app")
                .projectDescription("An android mobile application")
                .build();

        projectRepository.save(mobileApp);

    }

    private void initRolesData() {
        admin = Role.builder()
                .name("admin")
                .build();
        roleRepository.save(admin);

    }

    private void createTestUserWithRoles() {
        adminUser = User.builder()
                .userName("rk1234")
                .password("password")
                .email("ranjith@gmail.com")
                .firstName("ranjith")
                .lastName("kumar")
                .image(null)
                .build();
        adminUser.setRole(admin);
        adminUser.addProject(mobileApp);

        userRepository.save(adminUser);
        projectRepository.save(mobileApp); //update project with new user

    }

    private Issue initOpenIssuesData(User user, Project project) {
        return Issue.builder()
                .title("open issue title " + issueCounter)
                .issueDescription("open issue description " + issueCounter++)
                .openedBy(user)
                .closedBy(null)
                .issueStatus(IssueStatus.OPEN)
                .project(project)
                .build();
    }

    private Issue initClosedIssuesData(User user, Project project) {
        return Issue.builder()
                .title("closed issue title " + issueCounter)
                .issueDescription("closed issue description " + issueCounter++)
                .openedBy(user)
                .closedBy(user)
                .issueStatus(IssueStatus.CLOSED)
                .project(project)
                .build();
    }

    @Test
    public void sanityCheck(){
        assertEquals(1, userRepository.count());
        assertEquals(1, roleRepository.count());
        assertEquals(1, projectRepository.count());
        assertEquals(2, issueRepository.count());
    }

    @Test
    public void whenUserIsRetrieved_thenValidateAllTheRelationshipFields() {
        User user = userRepository.findById(adminUser.getId()).orElse(null);

        //check user exists
        assertNotNull(user);

        //check user and role relationship
        assertNotNull(user.getRole());
        assertEquals(admin.getName(), user.getRole().getName());

        //check user and project relationship
        assertNotNull(user.getProjects());
        assertEquals(1, user.getProjects().size());
    }

    @Test
    public void whenRoleIsRetrieved_thenValidateAllTheRelationshipFields(){
        Role role = roleRepository.findById(admin.getId()).orElse(null);

        //check role exists
        assertNotNull(role);

        //check role and user relationship
        assertNotNull(role.getUserSet());
        assertEquals(1, role.getUserSet().size());
    }

    @Test
    public void whenProjectIsRetrieved_thenValidateAllTheRelationshipFields() {
        Project project = projectRepository.findById(mobileApp.getId()).orElse(null);

        //check project exists
        assertNotNull(project);

        //check project and user relationship
        assertNotNull(project.getUsers());
        assertEquals(1, project.getUsers().size());

        //check project and issue relationship
        assertNotNull(project.getIssues());
        assertEquals(2, project.getIssues().size());
    }

    @Test
    public void whenOpenIssueIsRetrieved_thenValidateAllTheRelationshipFields() {
        Issue issue = issueRepository.findById(issueOpen.getId()).orElse(null);

        //check issue exists
        assertNotNull(issue);

        //check open status
        assertNotNull(issue.getOpenedBy());
        assertEquals(adminUser.getEmail(), issue.getOpenedBy().getEmail());

        //check closed status
        assertNull(issue.getClosedBy());

        //check project relationship
        assertNotNull(issue.getProject());
        assertEquals(mobileApp.getId(), issue.getProject().getId());
    }

    @Test
    public void whenClosedIssueIsRetrieved_thenValidateAllTheRelationshipFields() {
        Issue issue = issueRepository.findById(issueClosed.getId()).orElse(null);

        //check issue exists
        assertNotNull(issue);

        //check open status
        assertEquals(adminUser.getEmail(), issue.getOpenedBy().getEmail());

        //check closed status
        assertNotNull(issue.getClosedBy());
        assertEquals(adminUser.getEmail(), issue.getClosedBy().getEmail());


        //check project relationship
        assertNotNull(issue.getProject());
        assertEquals(mobileApp.getId(), issue.getProject().getId());
    }
}
