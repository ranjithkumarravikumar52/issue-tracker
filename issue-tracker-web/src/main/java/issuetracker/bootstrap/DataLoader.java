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

    public DataLoader(UserService userService, IssueService issueService, ProjectService projectService, RoleService roleService) {
        this.userService = userService;
        this.issueService = issueService;
        this.projectService = projectService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        //create dev and tester roles objects
        Role developer = Role.builder().name("developer").build();
        Role tester = Role.builder().name("tester").build();

        //save roles before we can set the child objects
        roleService.save(developer);
        roleService.save(tester);
        log.info("Saved role objects");

        //create 2 phone numbers
        PhoneNumber phoneNumber1 = PhoneNumber.builder().phoneNumber("1234567890").build();
        PhoneNumber phoneNumber2 = PhoneNumber.builder().phoneNumber("6789012345").build();

        //create johnDoe and janeDoe user objects
        //field objects
        User johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        User janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();

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
//        roleService.findById(1).getUserSet().iterator().forEachRemaining(user -> log.info(user.getUserName()));
//        roleService.findById(2).getUserSet().iterator().forEachRemaining(user -> log.info(user.getUserName()));

        //issue object
        Issue blockerIssue = Issue.builder().issueDescription("blocker issue").postedBy(johnDoe).build();
        Issue graphicsIssue = Issue.builder().issueDescription("graphics issue").postedBy(janeDoe).build();
        issueService.save(blockerIssue);
        issueService.save(graphicsIssue);
        log.info("Saved issue objects");


        //init 3 projects
        Project freePlay = Project.builder().projectDescription("Sims Free play").build();
        Project johnWick3 = Project.builder().projectDescription("John Wick 3").build();
        Project endgame = Project.builder().projectDescription("Endgame").build();
        projectService.save(freePlay);
        projectService.save(johnWick3);
        projectService.save(endgame);
        log.info("Saved project objects");

        //now updating user with projects should be validated in join table - Oh yeah it will fail, cos the relationship needs to be updated
        //from both direction or NOT?
        freePlay.setUsers(Collections.singleton(janeDoe));
        endgame.setUsers(Collections.singleton(johnDoe));
        johnWick3.getUsers().add(johnDoe);
        johnWick3.getUsers().add(janeDoe);
        projectService.save(freePlay);
        projectService.save(johnWick3);
        projectService.save(endgame);
        log.info("updated projects with users");
    }
}
