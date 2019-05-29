package issuetracker.bootstrap;

import issuetracker.entity.Issue;
import issuetracker.entity.Project;
import issuetracker.entity.Role;
import issuetracker.entity.User;
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

        //load data only if there is no data in the project repo
        if (projectService.findAll().size() == 0) {
            loadProjectsData();
        }

        //load data only if there is no data in the role repo
        if (roleService.findAll().size() == 0) {
            loadRolesData();
        }

    }

    private void loadUsersData() {
        johnDoe = User.builder()
                .id(1)
                .userName("johnDoe")
                .password("pass123")
                .email("johnDoe@gmail.com")
                .firstName("john")
                .lastName("doe")
                .build();

        janeDoe = User.builder()
                .id(2)
                .userName("janeDoe")
                .password("pass456")
                .email("janeDoe@gmail.com")
                .firstName("jane")
                .lastName("doe")
                .build();

        //save user objects
        userService.save(johnDoe);
        userService.save(janeDoe);
        log.info("Saved user objects...");
    }

    private void loadIssuesData() {
        Issue blockerIssue = Issue.builder()
                .id(1)
                .issueDescription("blocker issue")
                .postedBy(johnDoe)
                .openedBy(janeDoe)
                .fixedBy(johnDoe)
                .closedBy(janeDoe)
                .build();

        Issue graphicsIssue = Issue.builder()
                .id(2)
                .issueDescription("graphics issue")
                .postedBy(janeDoe)
                .openedBy(johnDoe)
                .fixedBy(janeDoe)
                .closedBy(johnDoe)
                .build();
        issueService.save(blockerIssue);
        issueService.save(graphicsIssue);
        log.info("Saved issue objects");
    }

    private void loadProjectsData() {
        Project freePlay = Project.builder().id(1).projectDescription("Sims Free play").userList(Arrays.asList(johnDoe, janeDoe)).build();
        Project johnWick3 = Project.builder().id(1).projectDescription("John Wick 3").userList(Arrays.asList(johnDoe, janeDoe)).build();
        Project endgame = Project.builder().id(1).projectDescription("Endgame").userList(Arrays.asList(johnDoe, janeDoe)).build();
        projectService.save(freePlay);
        projectService.save(johnWick3);
        projectService.save(endgame);
        log.info("Saved project objects");
    }

    private void loadRolesData() {
        Role developerRole = Role.builder().id(1).name("Developer").userList(Collections.singletonList(johnDoe)).build();
        Role testerRole = Role.builder().id(1).name("Tester").userList(Collections.singletonList(janeDoe)).build();
        roleService.save(developerRole);
        roleService.save(testerRole);
        log.info("Saved roles objects");
    }
}
