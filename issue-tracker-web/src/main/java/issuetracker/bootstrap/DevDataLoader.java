package issuetracker.bootstrap;

import issuetracker.entity.*;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("dev")
public class DevDataLoader implements CommandLineRunner {

    //static issue id counter
    private static int issueCounter = 1;

    private final UserService userService;
    private final IssueService issueService;
    private final ProjectService projectService;
    private final RoleService roleService;

    //5 roles
    private Role admin;
    private Role developer;
    private Role tester;
    private Role sales;
    private Role humanResources;

    //3 projects
    private Project mobileApp;
    private Project machineLearningDemo;
    private Project nlpProject;

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

        //2 lists - project and user for init issues and 1 list for issue to user saveAll() on issues
        List<Project> projectList = new ArrayList<>(projectService.findAll());
        List<User> userList = new ArrayList<>(userService.findAll());

        //each user post one open and one closed issue, for all the projects
        for(User user: userList){
            for(Project project : projectList){
                Issue issueOpen = initOpenIssuesData(user, project);
                Issue issueClosed = initClosedIssuesData(user, project);
                issueService.saveAll(Arrays.asList(issueOpen, issueClosed));
                projectService.save(project); //update project with issue relationship
            }
        }
    }

    private void initProjectsData() {
        //init 3 projects
        mobileApp = Project.builder()
                .title("mobile app")
                .projectDescription("An android mobile application")
                .build();
        machineLearningDemo = Project.builder()
                .title("machine learning demo")
                .projectDescription("Demo project to practice introduction to machine learning course")
                .build();
        nlpProject = Project.builder()
                .title("nlp assignment")
                .projectDescription("Social media sentiment analysis")
                .build();

        //save all the 3 projects
        projectService.saveAll(Arrays.asList(mobileApp, machineLearningDemo, nlpProject));

    }

    private void initRolesData() {
        //init 5 roles
        developer = Role.builder()
                .name("developer")
                .build();
        tester = Role.builder()
                .name("tester")
                .build();
        admin = Role.builder()
                .name("admin")
                .build();
        sales = Role.builder()
                .name("sales")
                .build();
        humanResources = Role.builder()
                .name("human resources")
                .build();

        //save all 5 roles
        roleService.saveAll(Arrays.asList(developer, tester, admin, sales, humanResources));

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
