package issuetracker.bootstrap;

import issuetracker.entity.Project;
import issuetracker.entity.Role;
import issuetracker.entity.User;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("dev")
public class DevDataLoader implements CommandLineRunner {

    @Value("#{new Integer('${dataloader.value}')}")
    private Integer FAKE_USER_DATA_COUNT;

    private static final String[] LOCALE_LIST = {"de-CH", "en-GB", "en-IND", "en-PAK", "en-US"};

    private final UserService userService;
    private final IssueService issueService;
    private final ProjectService projectService;
    private final RoleService roleService;
    private Role admin;
    private Role developer;
    private Role tester;
    private Role sales;
    private Role humanResources;

    private Project mobileApp;
    private Project machineLearningDemo;
    private Project nlpProject;
    private User adminUser = User.builder()
            .userName("rk1234")
            .password("password")
            .email("ranjith@gmail.com")
            .firstName("ranjith")
            .lastName("kumar")
            .image(null)
            .build();
    private User devUser = User.builder()
            .userName("dev123")
            .password("password")
            .email("dev@gmail.com")
            .firstName("john")
            .lastName("dev")
            .image(null)
            .build();
    private User testerUser = User.builder()
            .userName("test12")
            .password("password")
            .email("tester@gmail.com")
            .firstName("john")
            .lastName("tester")
            .image(null)
            .build();
    private User salesUser = User.builder()
            .userName("sales1")
            .password("password")
            .email("sales@gmail.com")
            .firstName("john")
            .lastName("sales")
            .image(null)
            .build();
    private User hrUser = User.builder()
            .userName("hr1234")
            .password("password")
            .email("hr@gmail.com")
            .firstName("john")
            .lastName("hr")
            .image(null)
            .build();


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

        roleService.saveAll(Arrays.asList(developer, tester, admin, sales, humanResources));

    }

    private void createTestUserWithRoles() {

    }

    private User initUsersData() {
        return null;
    }

    private void initIssuesData(User user) {

    }


}
