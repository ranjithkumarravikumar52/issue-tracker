package issuetracker.repository;

import issuetracker.entity.Issue;
import issuetracker.entity.IssueStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class UserAndIssuesRepositoryTest extends AbstractClassRepositoryTest {

    /**
     * object navigation - uni direction, issues -> user but 4 (posted, opened, fixed, closed) different ways
     */
    @Test
    public void objectNavigationFromIssueToUser() {
        //prep - get an issue record
        //issueDescription("blocker issue").postedBy(janeDoe).openedBy(johnDoe).fixedBy(johnDoe).closedBy(janeDoe)
        Issue actualIssue = issueRepository.findById(blockerIssue.getId())
                .orElse(null);

        //assertion - actualIssue is not null
        assertNotNull(actualIssue);

        //assertion - posted
        assertNotNull(actualIssue.getPostedBy());
        assertEquals(blockerIssue.getPostedBy(), actualIssue.getPostedBy());

        //assertion - opened
        assertNotNull(actualIssue.getOpenedBy());
        assertEquals(blockerIssue.getOpenedBy(), actualIssue.getOpenedBy());

        //assertion - fixed
        assertNotNull(actualIssue.getFixedBy());
        assertEquals(blockerIssue.getFixedBy(), actualIssue.getFixedBy());

        //assertion - fixed
        assertNotNull(actualIssue.getClosedBy());
        assertEquals(blockerIssue.getClosedBy(), actualIssue.getClosedBy());

    }

    /**
     * object navigation - is uni direction, user -> issue should not be accessible
     */
    @Test
    public void objectNavigationFromUserToIssue() {
//        johnDoe.getIssue() //doesn't exist
    }

    /**
     * issueDescription and postedBy can't be null/empty
     */
    @Test(expected = ConstraintViolationException.class)
    public void whenInvalidIssueIsSaved_thenThrowException() throws ConstraintViolationException {
        //prep - issueDescription is null and posted by is null
        Issue invalidIssue = Issue.builder()
                .build();

        //action - saved
        issueRepository.save(invalidIssue); //throws exception

    }

    /**
     * issueDescription and postedBy can't be null/empty, however other 3 user types in issues can be null
     */
    @Test
    public void whenValidIssueIsSaved_thenNoException() {
        //prep - create a valid issue
        Issue validIssue = Issue.builder()
                .issueDescription("Text issue")
                .postedBy(janeDoe)
                .openedBy(null)
                .fixedBy(null)
                .closedBy(null)
                .issueStatus(IssueStatus.OPEN)
                .build();

        //action - save
        Issue actualIssue = issueRepository.save(validIssue);

        //assert - throws no exception, savedObject is same as saveObject
        assertEquals(actualIssue, validIssue);

    }

    /**
     * When issueDescription is null
     */
    @Test(expected = ConstraintViolationException.class)
    public void whenNullIssueDescriptionIsSaved_thenThrowException() throws ConstraintViolationException {
        Issue validIssue = Issue.builder()
                .issueDescription(null)
                .postedBy(janeDoe)
                .openedBy(null)
                .fixedBy(null)
                .closedBy(null)
                .build();
        issueRepository.save(validIssue); //throws exception
    }

    /**
     * Two issues can't have same issue description
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void whenTwoIssuesWithSameDescriptionSaved_thenThrowException() {
        //prep - create a valid issue
        Issue validIssue1 = Issue.builder()
                .issueDescription("Text issue")
                .postedBy(janeDoe)
                .openedBy(null)
                .fixedBy(null)
                .closedBy(null)
                .issueStatus(IssueStatus.OPEN)
                .build();
        Issue validIssue2 = Issue.builder()
                .issueDescription("Text issue")
                .postedBy(johnDoe)
                .openedBy(null)
                .fixedBy(null)
                .closedBy(null)
                .issueStatus(IssueStatus.OPEN)
                .build();

        //action - saveAll
        issueRepository.saveAll(Arrays.asList(validIssue1, validIssue2)); //throw exception please - DataIntegrityViolationException

    }

}