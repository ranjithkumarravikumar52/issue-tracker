package issuetracker.repository;

import issuetracker.entity.PhoneNumber;
import issuetracker.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * <p>RunWith(SpringRunner.class) is used to provide a bridge between Spring Boot test features and JUnit. Whenever
 * we are using any Spring Boot testing features in out JUnit tests, this annotation will be required.</p>
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
 * <p>This class primarily tests the relationship between User and PhoneNumber and their uni-directional mapping.<br>
 * Here the user class is primary class and phone number is child class and tests are done to validate this.<br>
 * Cascading operation is done only on the owning side that is User.<br>
 * In addition, couple of methods on crud operation has been tested to validate our mapping of our entities.</p>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserAndPhoneNumberRepositoryTest extends AbstractClassRepositoryTest {

    /**
     * Tests to check object navigation from user -> phone
     */
    @Test
    public void objectNavigationFromUserToPhone() {
        //action - find johnDoe
        User user1 = userRepository.findById(johnDoe.getId())
                .orElse(null);

        //assertion - check phone number 1
        assertNotNull(user1);
        assertNotNull(user1.getPhoneNumber());
        assertEquals(phoneNumber1.getPhoneNumber(), user1.getPhoneNumber()
                .getPhoneNumber());

        //action - find jane doe
        User user2 = userRepository.findById(janeDoe.getId())
                .orElse(null);

        //assertion - check phone number 2
        assertNotNull(user2);
        assertNotNull(user2.getPhoneNumber());
        assertEquals(phoneNumber2.getPhoneNumber(), user2.getPhoneNumber()
                .getPhoneNumber());
    }

    @Test
    public void noObjectNavigationFromPhoneToUser() {
        //no access to navigate from phoneNumber to user
        //phoneNumber1.getUserName() //no such method
    }

    /**
     * Deleting user should also delete phone number
     */
    @Test(expected = NullPointerException.class)
    public void whenDeleteUser_thenDeletePhoneNumber() throws NullPointerException {
        //when - user is deleted
        userRepository.delete(johnDoe);

        //assertion - user should be deleted
        User user = userRepository.findById(johnDoe.getId())
                .orElse(null);
        assertNull(user);

        //assertion - phone number 1 should be deleted
        assertNull(user.getPhoneNumber()); //throws NullPointerException
    }

    /**
     * If we delete the phone number we don't want to delete the user
     */
    @Test
    public void whenDeletePhoneNumber_thenUserShouldNotBeDeleted() {
        //when - user is called
        User user = userRepository.findById(janeDoe.getId())
                .orElse(null);

        //action -  set their phone number to null
        assertNotNull(user);
        user.setPhoneNumber(null);

        //update user in the repo
        userRepository.save(janeDoe);

        //assertion - user should be not null
        User actualUser = userRepository.findById(janeDoe.getId())
                .orElse(null);
        assertNotNull(actualUser);

        //assertion - phone number should be null
        assertNull(actualUser.getPhoneNumber());

    }

    /**
     * Two users can't have same phone numbers
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void twoUsersShouldNotHaveSamePhoneNumbers() throws DataIntegrityViolationException {
        //prep - create a new phone number
        PhoneNumber phoneNumber3 = PhoneNumber.builder()
                .phoneNumber("12345678901")
                .build();

        //action
        johnDoe.setPhoneNumber(phoneNumber3);
        janeDoe.setPhoneNumber(phoneNumber3);

        //assertion
        userRepository.saveAll(Arrays.asList(johnDoe, janeDoe)); //gief mi exception plas - DataIntegrityViolationException
    }

}