package issuetracker.repository;

import issuetracker.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class AbstractClassRepositoryTest {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public IssueRepository issueRepository;
    @Autowired
    public ProjectRepository projectRepository;
    @Autowired
    public RoleRepository roleRepository;

    //fields
    //user fields
    protected User johnDoe;
    protected User janeDoe;
    protected User jimmyDoe;
    protected PhoneNumber phoneNumber1;
    protected PhoneNumber phoneNumber2;
    protected PhoneNumber phoneNumber3;
    protected Role developer;
    protected Role tester;
    protected Role admin;
    //issue fields
    protected Issue blockerIssue;
    protected Issue graphicsIssue;
    protected Issue textIssue;
    //project fields
    protected Project project1;
    protected Project project2;

    @Before
    public void setUp() throws Exception {
        //get three roles and save them
        List<Role> roles = getRoles();
        roleRepository.saveAll(roles);

        //get three projects and save them
        List<Project> projectList = getProjects();
        projectRepository.saveAll(projectList);

        //prep - get three users and save
        List<User> userList = getUsers();
        userRepository.saveAll(userList);

        updateProjectWithUsers(); //bi-directional relationship with project 1 and 3 users
        projectRepository.save(projectList.get(0)); //update

        updateRolesWithUsers(); //bi-directional relationship with user and role
        roleRepository.saveAll(roles); //update for the bi-directional relationship

        //create 3 issues and save them
        List<Issue> issueList = getIssues();
        issueRepository.saveAll(issueList);
    }

    private void updateRolesWithUsers() {
        developer.getUserSet()
                .add(johnDoe);
        tester.getUserSet()
                .add(janeDoe);
        admin.getUserSet()
                .add(jimmyDoe);
    }

    private void updateProjectWithUsers() {
        project1.getUsers()
                .add(johnDoe);
        project1.getUsers()
                .add(janeDoe);
        project1.getUsers()
                .add(jimmyDoe);
    }

    private List<Issue> getIssues() {
        blockerIssue =
                Issue.builder()
                        .issueDescription("blocker issue")
                        .postedBy(janeDoe)
                        .openedBy(johnDoe)
                        .fixedBy(johnDoe)
                        .closedBy(janeDoe)
                        .issueStatus(IssueStatus.OPEN)
                        .build();
        graphicsIssue =
                Issue.builder()
                        .issueDescription("graphics issue")
                        .postedBy(jimmyDoe)
                        .openedBy(johnDoe)
                        .fixedBy(jimmyDoe)
                        .closedBy(janeDoe)
                        .issueStatus(IssueStatus.OPEN)
                        .build();
        textIssue =
                Issue.builder()
                        .issueDescription("text issue")
                        .postedBy(johnDoe)
                        .openedBy(johnDoe)
                        .fixedBy(jimmyDoe)
                        .closedBy(janeDoe)
                        .issueStatus(IssueStatus.OPEN)
                        .build();
        return new ArrayList<>(Arrays.asList(blockerIssue, graphicsIssue, textIssue));
    }

    private List<User> getUsers() {
        johnDoe =
                User.builder()
                        .userName("johnDoe")
                        .password("pass123")
                        .email("johnDoe@gmail.com")
                        .firstName("john")
                        .lastName("doe")
                        .build();
        janeDoe =
                User.builder()
                        .userName("janeDoe")
                        .password("pass456")
                        .email("janeDoe@gmail.com")
                        .firstName("jane")
                        .lastName("doe")
                        .build();
        jimmyDoe = User.builder()
                .userName("jimmyDoe")
                .password("jimmypass")
                .email("jimmyDoee@gmail.com")
                .firstName(
                        "jimmy")
                .lastName("doe")
                .build();

        //prep - set role relationship with each user
        johnDoe.setRole(developer);
        janeDoe.setRole(tester);
        jimmyDoe.setRole(admin);

        //prep - get 3 phone numbers
        phoneNumber1 = PhoneNumber.builder()
                .phoneNumber("1234567890")
                .build();
        phoneNumber2 = PhoneNumber.builder()
                .phoneNumber("6789012345")
                .build();
        phoneNumber3 = PhoneNumber.builder()
                .phoneNumber("7891234560")
                .build();

        //prep - set phone relationship with each user
        johnDoe.setPhoneNumber(phoneNumber1);
        janeDoe.setPhoneNumber(phoneNumber2);
        jimmyDoe.setPhoneNumber(phoneNumber3);

        johnDoe.getProjects()
                .add(project1);
        janeDoe.getProjects()
                .add(project1);
        jimmyDoe.getProjects()
                .add(project1);

        return new ArrayList<>(Arrays.asList(johnDoe, janeDoe, jimmyDoe));
    }

    private List<Project> getProjects() {
        project1 = Project.builder()
                .title("mobile app")
                .projectDescription("An android mobile application")
                .build();
        project2 = Project.builder()
                .title("machine learning demo")
                .projectDescription("Demo project to practice " +
                        "introduction to machine learning course")
                .build();
        return new ArrayList<>(Arrays.asList(project1, project2));
    }

    private List<Role> getRoles() {
        developer = Role.builder()
                .name("developer")
                .build();
        tester = Role.builder()
                .name("tester")
                .build();
        admin = Role.builder()
                .name("admin")
                .build();
        List<Role> roles = new ArrayList<>(Arrays.asList(developer, tester, admin));
        return roles;
    }

    @Test
    public void sanityCheck() {
        assertEquals(3, userRepository.count());
        assertEquals(3, issueRepository.count());
        assertEquals(3, roleRepository.count());
        assertEquals(2, projectRepository.count());
    }
}
