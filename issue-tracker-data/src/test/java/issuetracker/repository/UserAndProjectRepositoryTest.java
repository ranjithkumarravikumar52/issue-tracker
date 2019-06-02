package issuetracker.repository;

import issuetracker.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
/**
 * User can work on multiple projects
 * A project can have multiple users
 * Bi-directional
 * No cascading delete
 * fetch type lazy
 */
public class UserAndProjectRepositoryTest {

    //log
    private static final Logger log = LoggerFactory.getLogger(UserAndProjectRepositoryTest.class);
    //dependencies injection
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private RoleRepository roleRepository;
    //fields
    private Role developer;
    private Role tester;
    private User johnDoe;
    private User janeDoe;
    private User jimmyDoe;
    private PhoneNumber phoneNumber1;
    private PhoneNumber phoneNumber2;
    private Issue blockerIssue;
    private Issue graphicsIssue;
    private Role admin;
    private PhoneNumber phoneNumber3;
    private Project freePlay;
    private Project johnWick3;
    private Project endgame;

    @Before
    public void setUp() throws Exception {
        developer = Role.builder().name("developer").build();
        tester = Role.builder().name("tester").build();
        admin = Role.builder().name("admin").build();

        roleRepository.saveAll(Arrays.asList(developer, tester, admin));

        //prep - get two users
        johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();
        jimmyDoe = User.builder().userName("jimmyDoe").password("jimmypass").email("jimmyDoee@gmail.com").firstName("jimmy").lastName("doe").build();

        //prep - set role relationship with each user
        johnDoe.setRole(developer);
        janeDoe.setRole(tester);
        jimmyDoe.setRole(admin);

        //prep - get 2 phone numbers
        phoneNumber1 = PhoneNumber.builder().phoneNumber("1234567890").build();
        phoneNumber2 = PhoneNumber.builder().phoneNumber("6789012345").build();
        phoneNumber3 = PhoneNumber.builder().phoneNumber("7891234560").build();

        //prep - set phone relationship with each user
        johnDoe.setPhoneNumber(phoneNumber1);
        janeDoe.setPhoneNumber(phoneNumber2);
        jimmyDoe.setPhoneNumber(phoneNumber3);

        //save user
        userRepository.saveAll(Arrays.asList(johnDoe, janeDoe, jimmyDoe));

        //bi-directional relationship with user and role
        developer.getUserSet().add(johnDoe);
        tester.getUserSet().add(janeDoe);
        admin.getUserSet().add(jimmyDoe);

        //update for the bi-directional relationship
        roleRepository.saveAll(Arrays.asList(developer, tester, admin));

        //create 2 issues with john and jane defaults to posted by in each of them
        blockerIssue = Issue.builder().issueDescription("blocker issue").postedBy(janeDoe).openedBy(johnDoe).fixedBy(johnDoe).closedBy(janeDoe).build();
        graphicsIssue = Issue.builder().issueDescription("graphics issue").postedBy(jimmyDoe).openedBy(johnDoe).fixedBy(jimmyDoe).closedBy(janeDoe).build();
        issueRepository.saveAll(Arrays.asList(blockerIssue, graphicsIssue));

        //init 3 projects
        freePlay = Project.builder().projectDescription("Sims Free play").build();
        johnWick3 = Project.builder().projectDescription("John Wick 3").build();
        endgame = Project.builder().projectDescription("Endgame").build();

        //now updating user with projects should be validated in join table
        freePlay.setUsers(Collections.singleton(janeDoe));
        endgame.setUsers(Collections.singleton(johnDoe));
        johnWick3.setUsers(Collections.singleton(jimmyDoe));

        //save projects
        projectRepository.saveAll(Arrays.asList(freePlay, johnWick3, endgame));
        log.info("updated projects with users");

        //updating users with projects for bi-directional relationship
        johnDoe.getProjects().add(endgame);

        jimmyDoe.getProjects().add(johnWick3);

        janeDoe.getProjects().add(freePlay);

        userRepository.saveAll(Arrays.asList(johnDoe, janeDoe, jimmyDoe));
        log.info("updated users with projects");


    }

    @Test
    public void sanityCheck() {
        assertEquals(3, userRepository.count());
        assertEquals(2, issueRepository.count());
        assertEquals(3, roleRepository.count());
        assertEquals(3, projectRepository.count());
    }

    //bi-directional check user->project
    @Test
    public void objectNavigationFromUserToProject(){
        User user = userRepository.findById(johnDoe.getId()).orElse(null);
        assertNotNull(user);
        assertNotNull(user.getProjects());
        assertEquals(johnDoe.getProjects(), user.getProjects());
        assertEquals(1, user.getProjects().size());
    }

    //bi-directional check project->user
    @Test
    public void objectNavigationFromProjectToUser(){
        Project project = projectRepository.findById(endgame.getId()).orElse(null);
        assertNotNull(project);
        assertNotNull(project.getUsers());
        assertEquals(endgame.getUsers(), project.getUsers());
        assertEquals(1, project.getUsers().size());
    }

    //save valid project

    //save invalid project - same project description

    //relationship - a user can be in multiple projects

    //relationship - a project can have multiple users

}