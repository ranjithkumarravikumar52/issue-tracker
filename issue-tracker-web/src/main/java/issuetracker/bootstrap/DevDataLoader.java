package issuetracker.bootstrap;

import issuetracker.entity.Issue;
import issuetracker.entity.IssueStatus;
import issuetracker.entity.Project;
import issuetracker.entity.User;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Profile("dev")
public class DevDataLoader extends AbstractDataLoader implements CommandLineRunner {

    //static issue id counter
    private static int issueCounter = 1;

    //5 users
    private User adminUser;
    private User devUser;
    private User testerUser;
    private User salesUser;
    private User hrUser;

    public DevDataLoader(UserService userService, IssueService issueService, ProjectService projectService,
                         RoleService roleService) {
        this.userService = userService;
        this.issueService = issueService;
        this.projectService = projectService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        initProjectsData();
        initRolesData();
        createTestUserWithRoles();

        //2 sets to store issues and projects, so that all of them could be saved using saveAll()
        Set<Issue> issueSet = new HashSet<>();
        Set<Project> projectSet = new HashSet<>();

        //each user post one open and one closed issue, for all the projects
        for (User user : userService.findAll()) {
            for (Project project : projectService.findAll()) {
                issueSet.add(initOpenIssuesData(user, project));
                issueSet.add(initClosedIssuesData(user, project));
                projectSet.add(project);
            }
        }
        issueService.saveAll(issueSet);
        projectService.saveAll(projectSet); //update project with issue relationship
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
        adminUser.addProject(machineLearningDemo);
        adminUser.addProject(nlpProject);
        adminUser.addProject(mobileApp);

        devUser = User.builder()
                .userName("dev123")
                .password("password")
                .email("dev@gmail.com")
                .firstName("john")
                .lastName("dev")
                .image(null)
                .build();
        devUser.setRole(developer);
        devUser.addProject(nlpProject);
        devUser.addProject(mobileApp);

        testerUser = User.builder()
                .userName("test12")
                .password("password")
                .email("tester@gmail.com")
                .firstName("john")
                .lastName("tester")
                .image(null)
                .build();
        testerUser.setRole(tester);
        testerUser.addProject(nlpProject);
        testerUser.addProject(mobileApp);

        salesUser = User.builder()
                .userName("sales1")
                .password("password")
                .email("sales@gmail.com")
                .firstName("john")
                .lastName("sales")
                .image(null)
                .build();
        salesUser.setRole(sales);
        salesUser.addProject(nlpProject);
        salesUser.addProject(mobileApp);

        hrUser = User.builder()
                .userName("hr1234")
                .password("password")
                .email("hr@gmail.com")
                .firstName("john")
                .lastName("hr")
                .image(null)
                .build();
        hrUser.setRole(humanResources);
        hrUser.addProject(nlpProject);
        hrUser.addProject(mobileApp);

        userService.saveAll(Arrays.asList(adminUser, devUser, testerUser, salesUser, hrUser));
        roleService.saveAll(Arrays.asList(developer, tester, admin, sales, humanResources)); //update roles relationship
        projectService.saveAll(Arrays.asList(mobileApp, machineLearningDemo, nlpProject)); //update project relationship

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


}
