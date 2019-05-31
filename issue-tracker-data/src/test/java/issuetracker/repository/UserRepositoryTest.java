package issuetracker.repository;

import issuetracker.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;

    /**
     * test:
     * //User is not many-many but many-one
     * //user has many-one with roles
     * //roles has one-many with user
     * //the direction is bi-directional
     */
    @Test
    public void userIsNotManyToManyWithRole() {
        //get 2 users

    }
}