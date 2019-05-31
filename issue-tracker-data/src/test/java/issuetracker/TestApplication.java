package issuetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Providing this class here for allowing tests for repo to be done.
 *
 * Check the <a href="https://github.com/ranjithkumarravikumar52/issue-tracker/issues/27">Issue</a>
 */
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}