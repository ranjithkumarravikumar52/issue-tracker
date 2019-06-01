package issuetracker.repository;

import issuetracker.entity.Issue;
import issuetracker.entity.PhoneNumber;
import issuetracker.entity.Role;
import issuetracker.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
/**
 *  object navigation - uni direction, issues -> user but 4 different ways
 *  owning side is issues - so FK is present in issues - 4 FKS
 *  issueDescription and postedBy can't be null/empty
 *  however other 3 user types in issues can be null
 *
 *  //relationship
 *  issues can be posted by any single user
 *  any single user can post, open, fix and close multiple issues
 *
 *  //validation
 *  two issues can't have same exact description
 */
public class UserAndIssuesRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private IssueRepository issueRepository;

    //fields
    private Role developer;
    private Role tester;
    private User johnDoe;
    private User janeDoe;
    private PhoneNumber phoneNumber1;
    private PhoneNumber phoneNumber2;
    private Issue blockerIssue;
    private Issue graphicsIssue;

    @Before
    public void setUp() throws Exception {
        developer = Role.builder().name("developer").build();
        tester = Role.builder().name("tester").build();
        roleRepository.saveAll(Arrays.asList(developer, tester));

        //prep - get two users
        johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();

        //prep - set role relationship with each user
        johnDoe.setRole(developer);
        janeDoe.setRole(tester);

        //prep - get 2 phone numbers
        phoneNumber1 = PhoneNumber.builder().phoneNumber("1234567890").build();
        phoneNumber2 = PhoneNumber.builder().phoneNumber("6789012345").build();

        //prep - set phone relationship with each user
        johnDoe.setPhoneNumber(phoneNumber1);
        janeDoe.setPhoneNumber(phoneNumber2);

        //save user
        userRepository.saveAll(Arrays.asList(johnDoe, janeDoe));

        //bi-directional relationship with user and role
        developer.getUserSet().add(johnDoe);
        tester.getUserSet().add(janeDoe);

        //update for the bi-directional relationship
        roleRepository.saveAll(Arrays.asList(developer, tester));

        //create 2 issues with john and jane defaults to posted by in each of them
        blockerIssue = Issue.builder().issueDescription("blocker issue").postedBy(johnDoe).build();
        graphicsIssue = Issue.builder().issueDescription("graphics issue").postedBy(janeDoe).build();
        issueRepository.saveAll(Arrays.asList(blockerIssue, graphicsIssue));
    }

    @Test
    public void sanityCheck() {
        assertEquals(2, userRepository.count());
        assertEquals(2, roleRepository.count());
        assertEquals(2, issueRepository.count());
    }

}