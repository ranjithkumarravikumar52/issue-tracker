package issuetracker.bootstrap;

import issuetracker.entity.*;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final UserService userService;
    private final IssueService issueService;
    private final ProjectService projectService;
    private final RoleService roleService;

    //field objects
    private User johnDoe;
    private User janeDoe;

    private Role developer;
    private Role tester;

    private PhoneNumber phoneNumber1;
    private PhoneNumber phoneNumber2;

    public DataLoader(UserService userService, IssueService issueService, ProjectService projectService, RoleService roleService) {
        this.userService = userService;
        this.issueService = issueService;
        this.projectService = projectService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        //load data only if there is no data in the user repository
        if (userService.findAll().size() == 0) {
            loadUsersData();
        }
        //load data only if there is no data in the issue repo
        if (issueService.findAll().size() == 0) {
            loadIssuesData();
        }

        /*//load data only if there is no data in the project repo
        if (projectService.findAll().size() == 0) {
            loadProjectsData();
        }

        //load data only if there is no data in the role repo
        if (roleService.findAll().size() == 0) {
            loadRolesData();
        }*/

    }

    private void loadUsersData() {

        //create dev and tester roles objects
        developer = Role.builder().id(1).name("developer").build();
        tester = Role.builder().id(2).name("tester").build();

        //save roles before we can set the child objects
        roleService.save(developer);
        roleService.save(tester);
        log.info("Saved role objects");

        //create 2 phone numbers
        phoneNumber1 = PhoneNumber.builder().id(1).phoneNumber("1234567890").build();
        phoneNumber2 = PhoneNumber.builder().id(2).phoneNumber("6789012345").build();

        //create johnDoe and janeDoe user objects
        johnDoe = User.builder().id(1).userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        janeDoe = User.builder().id(2).userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();

        //save the child objects for the roles
        //bi-directional relationship with roles and user
        developer.setUserSet(Collections.singleton(johnDoe));
        tester.setUserSet(Collections.singleton(janeDoe));

        //set the roles child objects for the user
        johnDoe.setRole(developer);
        janeDoe.setRole(tester);

        //set the phone number child objects for the user
        johnDoe.setPhoneNumber(phoneNumber1);
        janeDoe.setPhoneNumber(phoneNumber2);

        //save user objects
        userService.save(johnDoe);
        userService.save(janeDoe);
        log.info("Saved user objects...");

        //save roles before we can set the child objects
        roleService.save(developer);
        roleService.save(tester);
        log.info("Update role objects");

        //check if we can navigate from role to user
        roleService.findById(1).getUserSet().iterator().forEachRemaining(user -> log.info(user.getUserName()));
        roleService.findById(2).getUserSet().iterator().forEachRemaining(user -> log.info(user.getUserName()));
    }

    private void loadIssuesData() {
        Issue blockerIssue = Issue.builder().id(1).issueDescription("blocker issue").postedBy(johnDoe).build();
        Issue graphicsIssue = Issue.builder().id(2).issueDescription("graphics issue").postedBy(janeDoe).build();

        issueService.save(blockerIssue);
        issueService.save(graphicsIssue);
        log.info("Saved issue objects");
    }

   /* private void loadProjectsData() {
        Project freePlay = Project.builder().id(1).projectDescription("Sims Free play").userList(Arrays.asList(johnDoe, janeDoe)).build();
        Project johnWick3 = Project.builder().id(2).projectDescription("John Wick 3").userList(Arrays.asList(johnDoe, janeDoe)).build();
        Project endgame = Project.builder().id(3).projectDescription("Endgame").userList(Arrays.asList(johnDoe, janeDoe)).build();
        projectService.save(freePlay);
        projectService.save(johnWick3);
        projectService.save(endgame);
        log.info("Saved project objects");
    }

    private void loadRolesData() {
        Role developerRole = Role.builder().id(1).name("Developer").userList(Collections.singletonList(johnDoe)).build();
        Role testerRole = Role.builder().id(2).name("Tester").userList(Collections.singletonList(janeDoe)).build();
        roleService.save(developerRole);
        roleService.save(testerRole);
        log.info("Saved roles objects");
    }*/
}
