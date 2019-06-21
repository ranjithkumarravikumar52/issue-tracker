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

import java.util.Locale;
import java.util.Random;

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
        Project mobileApp = Project.builder()
                .title("mobile app")
                .projectDescription("An android mobile application")
                .build();
        Project machineLearningDemo = Project.builder()
                .title("machine learning demo")
                .projectDescription("Demo project to practice introduction to machine learning course")
                .build();
        Project nlpProject = Project.builder()
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

    private void createTestUserWithRoles(){
        //saving admin user
        User user = User.builder().userName("rk1234").password("password").email("ranjith@gmail.com").firstName(
                "ranjith").lastName("kumar").image(null).build();
        user.setRole(admin);
        userService.save(user);

        //saving dev user
        user = User.builder().userName("dev123").password("password").email("dev@gmail.com").firstName(
                "john").lastName("dev").image(null).build();
        user.setRole(developer);
        userService.save(user);

        //saving tester user
        user = User.builder().userName("test12").password("password").email("tester@gmail.com").firstName(
                "john").lastName("tester").image(null).build();
        user.setRole(tester);
        userService.save(user);

        //saving sales user
        user = User.builder().userName("sales1").password("password").email("sales@gmail.com").firstName(
                "john").lastName("sales").image(null).build();
        user.setRole(sales);
        userService.save(user);

        //saving hr user
        user = User.builder().userName("hr1234").password("password").email("hr@gmail.com").firstName(
                "john").lastName("hr").image(null).build();
        user.setRole(humanResources);
        userService.save(user);
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

        //update user with role
        Role role = updateUserWithRole(johnDoe);

        //update role with user
        updateRoleWithUser(johnDoe, role);

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

    private void updateRoleWithUser(User johnDoe, Role role) {
        role.getUserSet()
                .add(johnDoe);
        roleService.save(role);
    }

    private Role updateUserWithRole(User johnDoe) {
        int randomId = 1 + new Random().nextInt(5); //id is 1 based, hence added 1
        Role role = roleService.findById(randomId);
        johnDoe.setRole(role);
        userService.save(johnDoe);
        return role;
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
