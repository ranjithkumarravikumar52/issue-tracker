package issuetracker.repository;

import com.github.javafaker.Faker;
import issuetracker.entity.PhoneNumber;
import issuetracker.entity.Project;
import issuetracker.entity.Role;
import issuetracker.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.junit.Assert.*;

/**
 * This class is to understand how pagination based off the spring data commons. Not needed this tests necessarily for testing our repo class.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryPaginationTest {

    private static final int USERS_COUNT = 20;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {

        //when project is created
        Project freePlay = Project.builder().title("Sims Free Play").projectDescription("A mobile app game by EA games").build();;

        projectRepository.save(freePlay);

        //create dev role objects
        Role developer = Role.builder().name("developer").build();
        roleRepository.save(developer);

        //create few users for our test data
        for (int i = 0; i < USERS_COUNT; i++) {
            //faker init
            Faker faker = new Faker(new Locale("en-IND"));

            //create 2 phone numbers
            PhoneNumber phoneNumber = getPhoneNumber(faker);

            //get 2 users
            User johnDoe = getUser(faker);

            //set the roles child objects for the user
            johnDoe.setRole(developer);

            //set the phone number child objects for the user
            johnDoe.setPhoneNumber(phoneNumber);

            //save user objects
            userRepository.save(johnDoe);
        }
    }

    @Test
    public void findAllPageable_checkOverAllContent() {
        //Here, the page counts starts at zero - Assigning 10 elements to the first page
        int currentPage = 2;
        int pageSize = 7;
        Page<User> userPage = userRepository.findAll(PageRequest.of(currentPage, pageSize));

        assertNotNull(userPage);
        assertTrue(!userPage.isEmpty());

        //check over all content
        assertEquals(USERS_COUNT, userPage.getTotalElements());

        int actual;
        if (USERS_COUNT % pageSize == 0) {
            actual = USERS_COUNT / pageSize;
        } else {
            actual = (USERS_COUNT / pageSize) + 1;
        }

        //check total number of pages
        assertEquals(actual, userPage.getTotalPages());
    }

    @Test
    public void findAllPageable_checkEachPageContent_Valid() {
        int currentPage = 1;
        int pageSize = 7;
        Page<User> userPage = userRepository.findAll(PageRequest.of(currentPage, pageSize));

        assertNotNull(userPage);
        assertTrue(!userPage.isEmpty());

        //check current page size
        assertEquals(7, userPage.getContent().size());
    }

    @Test
    public void findAllPageable_checkEachPageContent_Invalid() {
        int currentPage = 3;
        int pageSize = 7;

        //pages exists for 0 ,1 and 2
        Page<User> userPage = userRepository.findAll(PageRequest.of(currentPage, pageSize));

        assertEquals("Page 4 of 3 containing UNKNOWN instances", userPage.toString());
        assertEquals(0, userPage.getNumberOfElements());
    }

    @Test
    public void findAllPageable_checkLastPage() {

        //Here, the page counts starts at zero - Assigning 10 elements to the first page
        int currentPage = 2;
        int pageSize = 7;
        Page<User> userPage = userRepository.findAll(PageRequest.of(currentPage, pageSize));

        assertNotNull(userPage);
        assertTrue(!userPage.isEmpty());

        //individual page check - current page should contain max pageSize elements
        assertEquals(6, userPage.getContent().size());
    }

    private PhoneNumber getPhoneNumber(Faker faker) {
        return PhoneNumber.builder().phoneNumber(faker.phoneNumber().phoneNumber()).build();
    }

    private User getUser(Faker faker) {
        //prep johnDoe fake data
        String firstName = faker.name().firstName().toLowerCase();
        String lastName = faker.name().firstName().toLowerCase();
        if (firstName.length() < 2) {
            firstName = firstName + faker.letterify("?");
        }
        String userName = lastName.charAt(0) + firstName.substring(0, 3) + faker.numerify("##");
        String password = faker.bothify("????####");
        String email = userName + "@gmail.com";


        //field objects
        return User.builder()
                .firstName(firstName).lastName(lastName)
                .userName(userName).password(password).email(email)
                .build();
    }

}