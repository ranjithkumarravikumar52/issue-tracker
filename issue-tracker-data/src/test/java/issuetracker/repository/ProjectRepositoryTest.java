package issuetracker.repository;

import issuetracker.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProjectRepositoryTest {
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
    private Issue textIssue;
    private Role admin;
    private PhoneNumber phoneNumber3;
    private Project project1;
    private Project project2;

    @Before
    public void setUp() throws Exception {
        developer = Role.builder().name("developer").build();
        tester = Role.builder().name("tester").build();
        admin = Role.builder().name("admin").build();

        roleRepository.saveAll(Arrays.asList(developer, tester, admin));

        //save projects
        project1 = Project.builder().title("mobile app").projectDescription("An android mobile application").build();
        project2 = Project.builder().title("machine learning demo").projectDescription("Demo project to practice introduction to machine learning course").build();
        projectRepository.saveAll(Arrays.asList(project1, project2));

        //prep - get two users
        johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();
        jimmyDoe = User.builder().userName("jimmyDoe").password("jimmypass").email("jimmyDoee@gmail.com").firstName("jimmy").lastName("doe").build();

        //prep - set role relationship with each user
        johnDoe.setRole(developer);
        janeDoe.setRole(tester);
        jimmyDoe.setRole(admin);

        //prep - get 3 phone numbers
        phoneNumber1 = PhoneNumber.builder().phoneNumber("1234567890").build();
        phoneNumber2 = PhoneNumber.builder().phoneNumber("6789012345").build();
        phoneNumber3 = PhoneNumber.builder().phoneNumber("7891234560").build();

        //prep - set phone relationship with each user
        johnDoe.setPhoneNumber(phoneNumber1);
        janeDoe.setPhoneNumber(phoneNumber2);
        jimmyDoe.setPhoneNumber(phoneNumber3);

        johnDoe.getProjects().add(project1);
        janeDoe.getProjects().add(project1);
        jimmyDoe.getProjects().add(project1);

        //save user
        userRepository.saveAll(Arrays.asList(johnDoe, janeDoe, jimmyDoe));

        //bi-directional relationship with project and user
        project1.getUsers().add(johnDoe);
        project1.getUsers().add(janeDoe);
        project1.getUsers().add(jimmyDoe);
        projectRepository.save(project1);


        //bi-directional relationship with user and role
        developer.getUserSet().add(johnDoe);
        tester.getUserSet().add(janeDoe);
        admin.getUserSet().add(jimmyDoe);

        //update for the bi-directional relationship
        roleRepository.saveAll(Arrays.asList(developer, tester, admin));

        //create 2 issues with john and jane defaults to posted by in each of them
        blockerIssue = Issue.builder().issueDescription("blocker issue").postedBy(janeDoe).openedBy(johnDoe).fixedBy(johnDoe).closedBy(janeDoe).build();
        graphicsIssue = Issue.builder().issueDescription("graphics issue").postedBy(jimmyDoe).openedBy(johnDoe).fixedBy(jimmyDoe).closedBy(janeDoe).build();
        textIssue = Issue.builder().issueDescription("text issue").postedBy(johnDoe).openedBy(johnDoe).fixedBy(jimmyDoe).closedBy(janeDoe).build();
        issueRepository.saveAll(Arrays.asList(blockerIssue, graphicsIssue, textIssue));
    }

    @Test
    public void findAllIssuesByProjectIdBy1_Success() {
        Project project = projectRepository.findById(project1.getId()).orElse(null);
        List<Object[]> allIssueByProjectId = projectRepository.findAllIssuesByProjectId(project.getId());
        assertNotNull(allIssueByProjectId);
        assertEquals(issueRepository.count(), allIssueByProjectId.size());
    }

    @Test
    public void findAllIssuesByProjectIdBy2_Success() {
        Project project = projectRepository.findById(project2.getId()).orElse(null);
        List<Object[]> allIssueByProjectId = projectRepository.findAllIssuesByProjectId(project.getId());
        assertEquals(0, allIssueByProjectId.size());
    }

    /**
     * There are three users in project 1 and no users in project 2
     */
    @Test
    public void findAllUsersByProjectIdBy1_Success(){
        Project project = projectRepository.findById(project1.getId()).orElse(null);
        assertNotNull(project);
        List<Object[]> allUsersByProjectId = projectRepository.findAllUsersByProjectId(project.getId());
        assertNotNull(allUsersByProjectId);
        assertEquals(3, allUsersByProjectId.size());

        project = projectRepository.findById(project2.getId()).orElse(null);
        assertNotNull(project);
        allUsersByProjectId = projectRepository.findAllUsersByProjectId(project.getId());
        assertNotNull(allUsersByProjectId);
        assertEquals(0, allUsersByProjectId.size());
    }

}