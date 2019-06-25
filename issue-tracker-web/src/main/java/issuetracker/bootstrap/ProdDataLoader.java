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
@Profile("prod")
public class ProdDataLoader implements CommandLineRunner {

    @Value("#{new Integer('${dataloader.value}')}")
    private Integer FAKE_USER_DATA_COUNT;

    private static final String[] LOCALE_LIST = {"de-CH", "en-GB", "en-IND", "en-PAK", "en-US"};

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


    public ProdDataLoader(UserService userService, IssueService issueService, ProjectService projectService,
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

        //init users based on data count
        for (int i = 0; i < FAKE_USER_DATA_COUNT; i++) {
            initUsersData();
        }

        //get me all users
        List<User> userList = new ArrayList<>(userService.findAll());
        //get me all projects
        List<Project> projectList = new ArrayList<>(projectService.findAll());

        //for each user, post one open and one closed issues, across all the projects
        for(Project project: projectList){
            for(User user: userList){
                Issue openIssue = initOpenIssuesData(user, project);
                Issue closedIssue = initClosedIssuesData(user, project);
                issueService.saveAll(Arrays.asList(openIssue, closedIssue)); //save 2 new issues
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

    private void initUsersData() {
        //faker init
        Faker faker = new Faker(new Locale(LOCALE_LIST[new Random().nextInt(LOCALE_LIST.length)]));

        //prep johnDoe fake data
        User johnDoe = getUser(faker);

        //set the phone number child objects for the user
        johnDoe.setPhoneNumber(getPhoneNumber(faker));

        //update user with random role
        Role role = getRandomRole();
        johnDoe.setRole(role);

        //update user and random project
        Project project = getRandomProject();
        johnDoe.addProject(project);

        userService.save(johnDoe);
        roleService.save(role); //update
        projectService.save(project); //update
    }

    private Role getRandomRole() {
        int randomId = 1 + new Random().nextInt(5); //id is 1 based, hence added 1
        return roleService.findById(randomId);
    }

    private Project getRandomProject() {
        int randomId = 1 + new Random().nextInt(3); //id is 1 based, hence added 1
        return projectService.findById(randomId);
    }

    private Issue initOpenIssuesData(User user, Project project) {
        return Issue.builder()
                .title(getIssueTitle())
                .issueDescription(getIssueDescription())
                .openedBy(user)
                .closedBy(null)
                .issueStatus(IssueStatus.OPEN)
                .project(project)
                .build();
    }

    private Issue initClosedIssuesData(User user, Project project) {
        return Issue.builder()
                .title(getIssueTitle())
                .issueDescription(getIssueDescription())
                .openedBy(user)
                .closedBy(user)
                .issueStatus(IssueStatus.CLOSED)
                .project(project)
                .build();
    }

    private User getUser(Faker faker) {
        String firstName = getUserFirstName(faker);
        String lastName = getUserLastName(faker);
        String userName = getUserName(firstName, lastName);
        String email = getEmail(userName);

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .password("password") //password default for users
                .email(email)
                .build();
    }

    private String getUserName(String firstName, String lastName) {
        Faker faker = new Faker();
        if (firstName.length() <= 2) {
            firstName = firstName + faker.letterify("?");
        }
        return lastName.charAt(0) + firstName.substring(0, 3) + faker.numerify("##");
    }

    private String getEmail(String userName) {
        return userName + "@gmail.com";
    }

    private String getUserLastName(Faker faker) {
        return faker.name()
                .lastName()
                .toLowerCase();
    }

    private String getUserFirstName(Faker faker) {
        return faker.name()
                .firstName()
                .toLowerCase();
    }

    private PhoneNumber getPhoneNumber(Faker faker) {
        return PhoneNumber.builder()
                .phoneNumber(faker.phoneNumber()
                        .phoneNumber())
                .build();
    }

    private String getIssueDescription() {
        return new Faker().gameOfThrones()
                .quote() + " - "
                + new Faker().gameOfThrones()
                .dragon()
                + new Faker().numerify("####");
    }

    private String getIssueTitle() {
        return new Faker().weather()
                .description() + " " + new Faker().address()
                .cityName() + " " + new Faker().numerify("####");
    }


}
