package issuetracker.bootstrap;

import com.github.javafaker.Faker;
import issuetracker.entity.*;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private static final int FAKE_USER_DATA_COUNT = 100;

    private final UserService userService;
    private final IssueService issueService;
    private final ProjectService projectService;
    private final RoleService roleService;
    private Project freePlay;
    private Project johnWick3;
    private Project endgame;
    private Role developer;
    private Role tester;
    private User johnDoe;
    private User janeDoe;
    private Issue blockerIssue;
    private Issue graphicsIssue;

    public DataLoader(UserService userService, IssueService issueService, ProjectService projectService, RoleService roleService) {
        this.userService = userService;
        this.issueService = issueService;
        this.projectService = projectService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {

        //independent project initialization
        //init only once
        //0. no dependencies on this
        initProjectsData();

        //independent role initialization
        //0. init only once
        initRolesData();

        for (int i = 0; i < FAKE_USER_DATA_COUNT; i++) {
            //independent user initialization
            //can be looped
            //1. needed roles for this
            initUsersData();

            //init only once
            //needed users done before this
            //CANNOT be looped, however can be put inside a loop only if issues == null.
            if (blockerIssue == null && graphicsIssue == null) {
                initIssuesData();
            }

            //update role with user data
            //can be looped
            //2. needed users and roles done before this
            updateRolesWithUser();

           /* //updating projects with user information
            //can be looped
            //4. needed projects, users and roles before this
            updateProjectsWithUser();*/

            //updating user with project information
            //can be looped
            //5. needed projects, users and roles before this
            updateUsersWithProjects();

            ;
        }

        //check - out of the loop
        debugRoleData();

        //check - out of the loop
        debugUserData();

        //check - out of the loop
        debugProjectData();
    }

    private void debugUserData() {
        log.info("Checking user information, their roles and their projects...\n");
        userService.findAll().forEach(user -> {
            log.info(Integer.toString(user.getId()));
            log.info(user.getUserName());
            log.info(user.getRole().getName());
            user.getProjects().forEach(project -> {
                log.info(project.getProjectDescription());
            });
        });
        log.info("Checked user information, their roles and their projects...\n");
    }

    private void debugRoleData() {
        log.info("Checking role - developer information...\n");
        developer.getUserSet().forEach(user -> {
            log.info(user.getUserName());
        });
        log.info("Checked role - developer information...\n");

        log.info("Checking role - tester information...\n");
        tester.getUserSet().forEach(user -> {
            log.info(user.getUserName());
        });
        log.info("Checked role - tester information...\n");
    }

    private void debugProjectData() {
        log.info("Checking project - freeplay information...\n");
        freePlay.getUsers().forEach(user -> {
            log.info(user.getUserName());
        });
        log.info("Checked project - freeplay information...\n");

        log.info("Checking project - endgame information...\n");
        endgame.getUsers().forEach(user -> {
            log.info(user.getUserName());
        });
        log.info("Checked project - endgame information...\n");

        log.info("Checking project - john wick information...\n");
        johnWick3.getUsers().forEach(user -> {
            log.info(user.getUserName());
        });
        log.info("Checked project - john wick information...\n");
    }

    private void updateUsersWithProjects() {
        //However object navigation will be broken if mapping is not done in either directions
        johnDoe.getProjects().add(freePlay);
        janeDoe.getProjects().add(endgame);

        johnDoe.getProjects().add(johnWick3);
        janeDoe.getProjects().add(johnWick3);
    }

    private void updateProjectsWithUser() {
        //Now updating user with projects should be validated in join table
        //Oh yeah it will fail, cos the relationship needs to be updated from both direction or NOT?
        //If you check the DB join tables, it fills up with accurately
        log.info("Assigning projects with user information...\n");
        freePlay.getUsers().add(johnDoe);
        endgame.getUsers().add(janeDoe);

        johnWick3.getUsers().add(johnDoe);
        johnWick3.getUsers().add(janeDoe);

        projectService.save(freePlay);
        projectService.save(johnWick3);
        projectService.save(endgame);
        log.info("Assigned projects with user information...\n");
    }

    private void updateRolesWithUser() {
        //save the child objects for the roles
        //bi-directional relationship with roles and user
        developer.getUserSet().add(johnDoe);
        tester.getUserSet().add(janeDoe);

        //save roles before we can set the child objects
        log.info("Updating role objects with their updated user list...\n");
        roleService.save(developer);
        roleService.save(tester);
        log.info("Updated role objects with their updated user list...\n");
    }

    private void initUsersData() {
        //faker init
        Faker faker = new Faker(new Locale("en-IND"));


        //create 2 phone numbers
        PhoneNumber phoneNumber1 = PhoneNumber.builder().phoneNumber(faker.phoneNumber().phoneNumber()).build();
        PhoneNumber phoneNumber2 = PhoneNumber.builder().phoneNumber(faker.phoneNumber().phoneNumber()).build();

        //prep johnDoe fake data
        //TODO extract method here
        String firstName = faker.name().firstName().toLowerCase();
        String lastName = faker.name().firstName().toLowerCase();
        if(firstName.length() < 2){
            firstName = firstName + faker.letterify("?");
        }
        String userName = lastName.charAt(0) + firstName.substring(0, 3) + faker.numerify("##");
        String password = faker.bothify("????####");
        String email = userName + "@gmail.com";


        //field objects
        johnDoe = User.builder()
                .firstName(firstName).lastName(lastName)
                .userName(userName).password(password).email(email)
                .build();

        //prep janeDoe fake data
        firstName = faker.name().firstName().toLowerCase();
        lastName = faker.name().lastName().toLowerCase();
        if(firstName.length() < 2){
            firstName = firstName + faker.letterify("?");
        }
        userName = lastName.charAt(0) + firstName.substring(0, 3) + faker.numerify("##");
        password = faker.bothify("????####");
        email = userName + "@gmail.com";

        janeDoe = User.builder()
                .firstName(firstName).lastName(lastName)
                .userName(userName).password(password).email(email)
                .build();

        //set the roles child objects for the user
        johnDoe.setRole(developer);
        janeDoe.setRole(tester);

        //set the phone number child objects for the user
        johnDoe.setPhoneNumber(phoneNumber1);
        janeDoe.setPhoneNumber(phoneNumber2);

        //save user objects
        log.info("Saving user objects with their roles and phone number information...\n");
        userService.save(johnDoe);
        userService.save(janeDoe);
        log.info("Saved user objects...\n");
    }

    private void initRolesData() {
        //create dev and tester roles objects
        developer = Role.builder().name("developer").build();
        tester = Role.builder().name("tester").build();

        //save roles before we can set the child objects
        log.info("Saving role objects...\n");
        roleService.save(developer);
        roleService.save(tester);
        log.info("Saved role objects.....\n");
    }

    private void initProjectsData() {
        //init 3 projects
        log.info("Saving project objects...\n");
        freePlay = Project.builder().projectDescription("Sims Free play").build();
        johnWick3 = Project.builder().projectDescription("John Wick 3").build();
        endgame = Project.builder().projectDescription("Endgame").build();
        projectService.save(freePlay);
        projectService.save(johnWick3);
        projectService.save(endgame);
        log.info("Saved project objects...\n");
    }

    private void initIssuesData() {
        //issue object
        log.info("Saving issue objects...\n");
        blockerIssue = Issue.builder().issueDescription("blocker issue").postedBy(johnDoe).build();
        graphicsIssue = Issue.builder().issueDescription("graphics issue").postedBy(janeDoe).build();
        issueService.save(blockerIssue);
        issueService.save(graphicsIssue);
        log.info("Saved issue objects...\n");
    }
}
