package issuetracker.bootstrap;

import com.github.javafaker.Faker;
import issuetracker.entity.*;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile({"dev", "prod"})
public class DataLoader implements CommandLineRunner {

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


    public DataLoader(UserService userService, IssueService issueService, ProjectService projectService,
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
        List<User> testUserList = new ArrayList<>(Arrays.asList(adminUser, devUser, testerUser, salesUser, hrUser));

        for (User user : testUserList) {
            initIssuesData(user);
            initIssuesData(user);
        }


        for (int i = 0; i < FAKE_USER_DATA_COUNT; i++) {
            User user = initUsersData();
            if (i % 2 == 0) {
                initIssuesData(user);
            }
            if (i % 3 == 0) {
                initIssuesData(user);
            }
            if (i % 5 == 0) {
                initIssuesData(user);
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

        projectService.save(mobileApp);
        projectService.save(machineLearningDemo);
        projectService.save(nlpProject);

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

        roleService.save(developer);
        roleService.save(tester);
        roleService.save(admin);
        roleService.save(sales);
        roleService.save(humanResources);

    }

    private void createTestUserWithRoles() {
        //saving admin user
        adminUser.setRole(admin);
        userService.save(adminUser);

        //saving dev user
        devUser.setRole(developer);
        userService.save(devUser);

        //saving tester user
        testerUser.setRole(tester);
        userService.save(testerUser);

        //saving sales user
        salesUser.setRole(sales);
        userService.save(salesUser);

        //saving hr user
        hrUser.setRole(humanResources);
        userService.save(hrUser);

        //update admin user with projects
        Set<Project> projectSet = new HashSet<>();
        projectSet.add(mobileApp);
        projectSet.add(machineLearningDemo);
        projectSet.add(nlpProject);
        adminUser.setProjects(projectSet);
        userService.save(adminUser);

        //update project with admin user
        mobileApp.getUsers()
                .add(adminUser);
        machineLearningDemo.getUsers()
                .add(adminUser);
        nlpProject.getUsers()
                .add(adminUser);
        projectService.save(mobileApp);
        projectService.save(machineLearningDemo);
        projectService.save(nlpProject);

        //update dev user with one project
        devUser.getProjects()
                .add(nlpProject);
        userService.save(devUser);


        Project project = projectService.findById(nlpProject.getId());
        project.getUsers()
                .add(devUser);
        projectService.save(project);


    }

    private User initUsersData() {
        //faker init
        Faker faker = new Faker(new Locale(LOCALE_LIST[new Random().nextInt(LOCALE_LIST.length)]));

        //create phone number
        PhoneNumber phoneNumber = getPhoneNumber(faker);

        //prep johnDoe fake data
        User johnDoe = getUser(faker);

        //set the phone number child objects for the user
        johnDoe.setPhoneNumber(phoneNumber);

        //save user objects - with phone number
        userService.save(johnDoe);

        //update user and role
        updateUserAndRole(johnDoe);

        //update project with user
        Project projectById = updateProjectWithUser(johnDoe);

        //update user with project
        updateUserWithProject(johnDoe, projectById);

        //return user
        return johnDoe;
    }

    private void initIssuesData(User user) {
        int i = user.getId();
        User userById = userService.findById(i);
        Issue issue = Issue.builder()
                .title(new Faker().weather()
                        .description() + " " + new Faker().address()
                        .cityName() + " " + new Faker().numerify("####"))
                .issueDescription(
                        new Faker().gameOfThrones()
                                .quote() + " - "
                                + new Faker().gameOfThrones()
                                .dragon()
                                + new Faker().numerify("####")
                )
                .openedBy(userById)
                .closedBy(userById)
                .issueStatus(IssueStatus.CLOSED)
                .build();

        //even issue are opened issues
        if (i % 2 == 0) {
            issue.setClosedBy(null);
            issue.setIssueStatus(IssueStatus.OPEN);
        }
        // i % 3 == 0 then mobileApp
        if (i % 3 == 0) {
            issue.setProject(mobileApp);
        }
        // i % 3 == 1 then machineLearningDemo
        if (i % 3 == 1) {
            issue.setProject(machineLearningDemo);
        }
        // i % 3 == 2 then nlpProject
        if (i % 3 == 2) {
            issue.setProject(nlpProject);
        }
        issueService.save(issue);
    }

    private User getUser(Faker faker) {
        String firstName = faker.name()
                .firstName()
                .toLowerCase();
        String lastName = faker.name()
                .firstName()
                .toLowerCase();

        if (firstName.length() <= 2) {
            firstName = firstName + faker.letterify("?");
        }

        String userName = lastName.charAt(0) + firstName.substring(0, 3) + faker.numerify("##");
        String password = faker.bothify("????####");
        String email = userName + "@gmail.com";

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .password(password)
                .email(email)
                .build();
    }

    private PhoneNumber getPhoneNumber(Faker faker) {
        return PhoneNumber.builder()
                .phoneNumber(faker.phoneNumber()
                        .phoneNumber())
                .build();
    }

    private void updateUserAndRole(User johnDoe) {
        int randomId = 1 + new Random().nextInt(5); //id is 1 based, hence added 1
        Role role = roleService.findById(randomId);
        johnDoe.setRole(role);
        userService.save(johnDoe);
        roleService.save(role);
    }

    private void updateUserWithProject(User johnDoe, Project projectById) {
        johnDoe.getProjects()
                .add(projectById);
        userService.save(johnDoe);
    }

    private Project updateProjectWithUser(User johnDoe) {
        int randomId = 1 + new Random().nextInt(3); //id is 1 based, hence added 1
        Project projectById = projectService.findById(randomId);
        projectById.getUsers()
                .add(johnDoe);
        projectService.save(projectById);
        return projectById;
    }


}
