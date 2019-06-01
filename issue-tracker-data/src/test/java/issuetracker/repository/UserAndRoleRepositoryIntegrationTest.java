package issuetracker.repository;

import issuetracker.entity.Role;
import issuetracker.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
 *
 * <br>
 *     <p>This class primarily tests the relationship between User and Role and their bi-directional mapping. In addition, couple of methods on crud operation has been tested to validate our mapping of our entities.</p>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserAndRoleRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private User johnDoe;
    private User janeDoe;
    private Role developer;
    private Role tester;

    @Before
    public void setup(){
        //prep - save dev and tester roles
        developer = Role.builder().name("developer").build();
        tester = Role.builder().name("tester").build();
        roleRepository.saveAll(Arrays.asList(developer, tester));

        //prep - save john and jane users
        johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();
        johnDoe.setRole(developer);
        janeDoe.setRole(tester);
        userRepository.saveAll(Arrays.asList(johnDoe, janeDoe));

        //updating role->user direction
        developer.getUserSet().add(johnDoe);
        tester.getUserSet().add(janeDoe);
        roleRepository.saveAll(Arrays.asList(developer, tester));

    }

    /**
     * Test for navigation from user object to role object
     */
    @Test
    public void userToRoleObjectTreeNavigation() {
        //when - user with an associated role object is saved
        //assertion - checking object tree navigation from user to role
        User user = userRepository.findById(johnDoe.getId()).orElse(null);
        assertNotNull(user);
        assertEquals("developer", user.getRole().getName());
    }

    /**
     * object navigation from role to user
     */
    @Test
    public void roleToUserObjectTreeNavigation() {
        //when - user and associated and role is saved
        //assert - roleName and role->user->roles->role[0]->roleName
        Role role = roleRepository.findById(tester.getId()).orElse(null);
        assertNotNull(role);
        assertEquals(tester.getName(), role.getUserSet().toArray(new User[0])[0].getRole().getName());
    }

    /**
     * when only user object is saved without saving role then check that specific records exists
     */
    @Test
    public void whenUserIsSaved_thenRecordExistsInUserRepo() {
        //when - only user object is saved without saving role then check that specific records exists
        User johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        Role developer = Role.builder().name("developer").build();
        johnDoe.setRole(developer);
        userRepository.save(johnDoe);

        //assert - record saved and selected are same
        User user = userRepository.findById(johnDoe.getId()).orElse(null);
        assertNotNull(user);
        assertEquals(johnDoe.getUserName(), user.getUserName());
    }

    /**
     * Since our mapping between user and role doesn't specify any cascade types, save operation doesn't cascade to roles.
     * However by doing so will throw InvalidDataAccessApiUsageException
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void whenAnUserIsSaved_thenRoleFindAllThrowsException() {
        //when - user is saved but the associated role is not saved
        User johnDoe = User.builder().userName("johnDoe").password("pass123").email("johnDoe@gmail.com").firstName("john").lastName("doe").build();
        Role developer = Role.builder().name("developer").build();
        johnDoe.setRole(developer);
        userRepository.save(johnDoe);

        //assert - accessing role repo should throw exception
        roleRepository.findAll(); //throws exception
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void whenAnUserIsSaved_thenUserFindAllThrowsException() {
        //when - user is saved but the associated role is not saved
        User janeDoe = User.builder().userName("janeDoe").password("pass456").email("janeDoe@gmail.com").firstName("jane").lastName("doe").build();
        Role developer = Role.builder().name("developer").build();
        janeDoe.setRole(developer);
        userRepository.save(janeDoe);

        //assert - accessing role repo should throw exception
        userRepository.findAll(); //throws exception
    }

    /**
     * findAll works from the user side only if both and user and role have been saved
     */
    @Test
    public void whenUserAndRoleAreSaved_thenUserFindAllWorks() {
        //action - findAll
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add); //shouldn't throw an exception

        //assert - only two records should exist
        assertEquals(2, userList.size());
    }

    /**
     * Deleting an user shouldn't delete the role
     */
    @Test
    public void whenAnUserIsDeleted_thenRoleShouldNotBeDeleted() {
        //action - delete
        userRepository.delete(johnDoe);

        //assertion - 1. roleRepo should still hold dev role
        Role role = roleRepository.findById(developer.getId()).orElse(null);
        assertNotNull(role);
        assertEquals(developer.getName(), role.getName());

        //assertion - 2. user repo shouldn't hold john doe
        User user = userRepository.findById(johnDoe.getId()).orElse(null);
        assertNull(user);

    }

    /**
     * When user and role objects are saved and their corresponding direction is established, then calling findById should return the user,
     * else there's something wrong with our database or test config setup
     */
    @Test
    public void whenUserFindById_thenReturnAnUser() {
        //action - findById
        User user = userRepository.findById(johnDoe.getId()).orElse(null);

        //assert - findById should return user
        assertNotNull(user);
        assertEquals(johnDoe.getUserName(), user.getUserName());
    }

    /**
     * when an user is deleted then the expected behavior is user should be deleted however the associated role object shouldn't.
     */
    @Test
    public void whenDeleteAnUser_thenDeleteSuccess() {
        //action - delete
        userRepository.delete(johnDoe);

        //1. assertion - user should be deleted
        User user = userRepository.findById(johnDoe.getId()).orElse(null);
        assertNull(user);

        //2. assertion - role shouldn't be deleted
        Role role = roleRepository.findById(developer.getId()).orElse(null);
        assertNotNull(role);
        assertEquals(developer.getName(), role.getName());
    }

    /**
     * when a role is deleted then the expected behavior is role should be deleted however the associated user object shouldn't.
     */
    @Test
    public void whenDeleteAnRole_thenDeleteSuccess() {
        //action - delete role
        roleRepository.delete(tester);

        //1. assertion - role should be deleted
        Role role = roleRepository.findById(tester.getId()).orElse(null);
        assertNull(role);

        //2. assertion - user shouldn't be deleted
        User user = userRepository.findById(janeDoe.getId()).orElse(null);
        assertNotNull(user);
        assertEquals(janeDoe.getUserName(), user.getUserName());

    }


}