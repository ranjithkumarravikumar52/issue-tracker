package issuetracker.repository;

import issuetracker.entity.Role;
import issuetracker.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * <p>RunWith(SpringRunner.class) is used to provide a bridge between Spring Boot test features and JUnit. Whenever we are using any Spring Boot testing features in out JUnit tests, this annotation will be required.</p>
 * <p>DataJpaTest provides some standard setup needed for testing the persistence layer:</p>
 * <ul>
 * <li>Configuring H2, an in-memory database</li>
 * <li>Setting Hibernate, Spring Data, and the DataSource</li>
 * <li>Performing an @EntityScan</li>
 * <li>Turning on SQL logging</li>
 * <li>By default, data JPA tests are transactional and roll back at the end of each test.</li>
 * </ul>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserAndRoleRepositoryIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(UserAndRoleRepositoryIntegrationTest.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Test for navigation from user object to role object
     */
    @Test
    public void userToRoleObjectTreeNavigation() {
        //when
        User johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        Role developer = Role.builder().name("developer").build();
        johnDoe.setRole(developer);
        userRepository.save(johnDoe);

        //assertion checking object tree navigation from user to role
        Optional<User> byId = userRepository.findById(1);
        if (byId.isPresent()) {
            System.out.println(byId.get().getUserName());
            System.out.println(byId.get().getRole().getName());
            assertEquals("developer", byId.get().getRole().getName());
        }
    }

    /**
     * object navigation from role to user
     */
    @Test
    public void roleToUserObjectTreeNavigation() {
        Role tester = Role.builder().name("tester").build();
        roleRepository.save(tester);

        User janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();
        janeDoe.setRole(tester);
        userRepository.save(janeDoe);

        tester.getUserSet().add(janeDoe);
        roleRepository.save(tester); //update

        //navigation check
        Optional<Role> roleRepositoryById = roleRepository.findById(1);
        roleRepositoryById.ifPresent(role -> role.getUserSet().forEach(user -> {
            log.info(user.getUserName());
            assertEquals(tester.getName(), user.getRole().getName());
        }));
    }

    /**
     * when only user object is saved then check that specific records exists
     */
    @Test
    public void whenUserIsSaved_thenRecordExistsInUserRepo() {
        //when
        User johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        Role developer = Role.builder().name("developer").build();
        johnDoe.setRole(developer);
        userRepository.save(johnDoe);

        //then
        Optional<User> optionalUser = userRepository.findById(1);
        optionalUser.ifPresent(user -> assertEquals(johnDoe, user));
    }

    /**
     * Since our mapping between user and role doesn't specify any cascade types, save operation doesn't cascade to roles.
     * However by doing so will throw InvalidDataAccessApiUsageException
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void whenAnUserIsSaved_thenRoleFindAllThrowsException() {
        User johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        Role developer = Role.builder().name("developer").build();
        johnDoe.setRole(developer);
        userRepository.save(johnDoe);
        roleRepository.findAll(); //throws exception
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void whenAnUserIsSaved_thenUserFindAllThrowsException() {
        User janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();
        Role developer = Role.builder().name("developer").build();
        janeDoe.setRole(developer);
        userRepository.save(janeDoe);
        userRepository.findAll(); //throws exception
    }

    /**
     * findAll works from the user side only if both and user and role have been saved
     */
    @Test
    public void whenUserAndRoleAreSaved_thenUserFindAllWorks() {
        Role tester = Role.builder().name("tester").build();
        roleRepository.save(tester);

        User janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();
        janeDoe.setRole(tester);
        userRepository.save(janeDoe);

        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add); //shouldn't throw an exception
        assertEquals(1, userList.size());

    }

    /**
     * Deleting an user shouldn't delete the role
     */
    @Test
    public void whenAnUserIsDeleted_thenRoleShouldNotBeDeleted() {
        //when
        Role developer = Role.builder().name("developer").build();
        roleRepository.save(developer);

        User johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        johnDoe.setRole(developer);
        userRepository.save(johnDoe);

        developer.getUserSet().add(johnDoe);
        roleRepository.save(developer); //update bi-directional relationship

        //action
        userRepository.delete(johnDoe);

        //then
        // 1. roleRepo should still hold dev role
        Optional<Role> roleOptional = roleRepository.findById(1);
        if (roleOptional.isPresent()) {
            assertEquals(developer, roleOptional.get());
        } else {
            throw new AssertionError("Not equals");
        }

        //then
        //2. user repo shouldn't hold john doe
        Optional<User> userRepositoryByIdd = userRepository.findById(1);
        if(userRepositoryByIdd.isPresent()){
            throw new AssertionError("user failed to delete");
        }else{
            assertNull(userRepositoryByIdd.orElse(null));
        }


    }


}