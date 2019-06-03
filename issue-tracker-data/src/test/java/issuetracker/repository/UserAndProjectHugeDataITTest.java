package issuetracker.repository;

import com.github.javafaker.Faker;
import issuetracker.entity.PhoneNumber;
import issuetracker.entity.Project;
import issuetracker.entity.Role;
import issuetracker.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserAndProjectHugeDataITTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {

        //when project is created
        Project freePlay = Project.builder().projectDescription("Sims Free play").build();
        Project endGame = Project.builder().projectDescription("End Game").build();
        projectRepository.save(freePlay);
        projectRepository.save(endGame);

        //create dev role objects
        Role developer = Role.builder().name("developer").build();
        Role tester = Role.builder().name("tester").build();
        roleRepository.save(developer);
        roleRepository.save(tester);

        for (int i = 0; i < 20; i++) {
            //faker init
            Faker faker = new Faker(new Locale("en-IND"));

            //create 2 phone numbers
            PhoneNumber phoneNumber1 = getPhoneNumber(faker);
            PhoneNumber phoneNumber2 = getPhoneNumber(faker);

            //get 2 users
            User johnDoe = getUser(faker);
            User janeDoe = getUser(faker);

            //set the roles child objects for the user
            johnDoe.setRole(developer);
            janeDoe.setRole(tester);

            //set the phone number child objects for the user
            johnDoe.setPhoneNumber(phoneNumber1);
            janeDoe.setPhoneNumber(phoneNumber2);

            //save user objects
            userRepository.save(johnDoe);
            userRepository.save(janeDoe);

            //setting up project -> user relationship
            freePlay.getUsers().add(johnDoe);
            endGame.getUsers().add(janeDoe);

            projectRepository.save(freePlay);
            projectRepository.save(endGame);

            //setting up user -> project relationship
            johnDoe.getProjects().add(freePlay);
            janeDoe.getProjects().add(endGame);

            userRepository.save(johnDoe);
            userRepository.save(janeDoe);

            //assertion
            assertNotNull(freePlay.getUsers());
            assertNotNull(endGame.getUsers());

            assertNotNull(johnDoe.getProjects());
            assertNotNull(janeDoe.getProjects());

            //check
            assertTrue(freePlay.getUsers().contains(johnDoe));
            assertTrue(endGame.getUsers().contains(janeDoe));

            assertTrue(johnDoe.getProjects().contains(freePlay));
            assertTrue(janeDoe.getProjects().contains(endGame));
        }

        Project actualProject = projectRepository.findById(freePlay.getId()).orElse(null);
        assertNotNull(actualProject);
        assertNotNull(actualProject.getUsers());
        actualProject.getUsers().forEach(user -> System.out.println("User id : "+user.getId()));
        System.out.println();

        actualProject = projectRepository.findById(endGame.getId()).orElse(null);
        assertNotNull(actualProject);
        assertNotNull(actualProject.getUsers());
        actualProject.getUsers().forEach(user -> System.out.println("User id : "+user.getId()));
        System.out.println();

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
