package issuetracker.bootstrap;

import com.github.javafaker.Faker;
import issuetracker.entity.*;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@Profile("prod")
public class ProdDataLoader implements CommandLineRunner {

    private static final Logger log = getLogger(ProdDataLoader.class);

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

    //2 lists
    private List<Role> roleList = new ArrayList<>();
    private List<Project> projectList = new ArrayList<>();


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
        log.info("finished init projects data");
        initRolesData();
        log.info("finished init roles data");
        initUsersData();
        log.info("finished init users data");
        initIssuesData();
        log.info("finished init issues data");
    }

    /**
     * for each user, post one open and one closed issues, across all the projects
     * for 3 projects and 100 users, total issues 600, open issues 300
     * for 1 project, 200 issues, 100 open and 100 closed
     */
    private void initIssuesData() {
        Set<Issue> issueSet = new HashSet<>();
        Set<Project> projectSet = new HashSet<>();

        for (Project project : projectService.findAll()) {
            for (User user : userService.findAll()) {
                Issue openIssue = initOpenIssuesData(user, project);
                Issue closedIssue = initClosedIssuesData(user, project);
                issueSet.add(openIssue);
                issueSet.add(closedIssue);
                projectSet.add(project);
            }
        }

        issueService.saveAll(issueSet); //save all issues
        projectService.saveAll(projectSet); //update project with issue relationship
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

        Iterable<Project> projects = projectService.saveAll(Arrays.asList(mobileApp, machineLearningDemo, nlpProject));
        projects.forEach(projectList::add);

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

        Iterable<Role> roles = roleService.saveAll(Arrays.asList(developer, tester, admin, sales, humanResources));
        roles.forEach(roleList::add);

    }

    private void initUsersData() {
        Set<User> userSet = new HashSet<>();
        Set<Role> roleSet = new HashSet<>();
        Set<Project> projectSet = new HashSet<>();

        for (int i = 0; i < FAKE_USER_DATA_COUNT; i++) {
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

            userSet.add(johnDoe);
            roleSet.add(role);
            projectSet.add(project);
        }
        userService.saveAll(userSet);
        roleService.saveAll(roleSet); //update
        projectService.saveAll(projectSet); //update
    }

    private Role getRandomRole() {
        return roleList.get(new Random().nextInt(5));
    }

    private Project getRandomProject() {
        return projectList.get(new Random().nextInt(3));
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
