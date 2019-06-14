package issuetracker.repository;

import issuetracker.entity.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProjectRepositoryTest extends AbstractClassRepositoryTest {
    @Test
    public void findAllIssuesByProjectIdBy1_Success() {
        Project project = projectRepository.findById(project1.getId())
                .orElse(null);
        List<Object[]> allIssueByProjectId = projectRepository.findAllIssuesByProjectId(project.getId());
        assertNotNull(allIssueByProjectId);
        assertEquals(issueRepository.count(), allIssueByProjectId.size());
    }

    @Test
    public void findAllIssuesByProjectIdBy2_Success() {
        Project project = projectRepository.findById(project2.getId())
                .orElse(null);
        List<Object[]> allIssueByProjectId = projectRepository.findAllIssuesByProjectId(project.getId());
        assertEquals(0, allIssueByProjectId.size());
    }

    /**
     * There are three users in project 1 and no users in project 2
     */
    @Test
    public void findAllUsersByProjectIdBy1_Success() {
        Project project = projectRepository.findById(project1.getId())
                .orElse(null);
        assertNotNull(project);
        List<Object[]> allUsersByProjectId = projectRepository.findAllUsersByProjectId(project.getId());
        assertNotNull(allUsersByProjectId);
        assertEquals(3, allUsersByProjectId.size());

        project = projectRepository.findById(project2.getId())
                .orElse(null);
        assertNotNull(project);
        allUsersByProjectId = projectRepository.findAllUsersByProjectId(project.getId());
        assertNotNull(allUsersByProjectId);
        assertEquals(0, allUsersByProjectId.size());
    }

}