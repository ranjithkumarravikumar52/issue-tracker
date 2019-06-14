package issuetracker.repository;

import issuetracker.entity.Project;
import issuetracker.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
/**
 * User can work on multiple projects
 * A project can have multiple users
 * Bi-directional
 * No cascading delete
 * fetch type lazy
 */
public class UserAndProjectRepositoryTest extends AbstractClassRepositoryTest {

    //bi-directional check user->project
    @Test
    public void objectNavigationFromUserToProject() {
        User user = userRepository.findById(johnDoe.getId()).orElse(null);
        assertNotNull(user);
        assertNotNull(user.getProjects());
        assertEquals(johnDoe.getProjects(), user.getProjects());
        assertEquals(1, user.getProjects().size());
    }

    //bi-directional check project->user
    @Test
    public void objectNavigationFromProjectToUser() {
        Project project = projectRepository.findById(project1.getId()).orElse(null);
        assertNotNull(project);
        assertNotNull(project.getUsers());
        assertEquals(project1.getUsers(), project.getUsers());
        assertEquals(3, project.getUsers().size());
    }

    //save valid project
    @Test
    public void saveValidProject() {
        Project project = Project.builder().title("title 1").projectDescription("description 1").build();
        project.getUsers().add(jimmyDoe);
        Project savedProject = projectRepository.save(project);
        assertNotNull(savedProject);
        assertNotNull(savedProject.getProjectDescription());
        assertEquals(savedProject, project);
    }

    //save invalid project - same project description
    @Test(expected = ConstraintViolationException.class)
    public void whenInvalidSaveProject_thenThrowException() {
        Project project = Project.builder().projectDescription(null).build();
        project.getUsers().add(jimmyDoe);
        projectRepository.save(project); //throw exception please
    }

    //save invalid project - two projects with same title
    @Test(expected = DataIntegrityViolationException.class)
    public void whenTwoProjectWithSameNameSaved_thenThrowException() {
        Project project1 = Project.builder().title("title 1").projectDescription("description 1").build();
        Project project2 = Project.builder().title("title 1").projectDescription("description 2").build();
        project1.getUsers().add(johnDoe);
        project2.getUsers().add(janeDoe);
        projectRepository.saveAll(Arrays.asList(project1, project2)); //throw exception please
    }

    //relationship - a user can be in multiple projects
    @Test
    public void userCanBeInMultipleProject() {
        Project project1 = Project.builder().title("title 1").projectDescription("description 1").build();
        Project project2 = Project.builder().title("title 2").projectDescription("description 2").build();
        User randyUser = User.builder()
                .userName("randyDoe").email("randyDoe@gmail.com").firstName("randy").lastName("doe").password("randypass").build();
        //saving project before saving user will throw an error
        userRepository.save(randyUser);

        //action - save user with multiple projects
        project1.getUsers().add(randyUser);
        project2.getUsers().add(randyUser);
        projectRepository.saveAll(Arrays.asList(project1, project2));

    }

    //relationship - a project can have multiple users
    @Test
    public void projectCanHaveMultipleUsers() {
        Project project1 = Project.builder().title("title 1").projectDescription("description 1").build();
        User randyUser1 = User.builder()
                .userName("randyDoe1").email("randy1Doe@gmail.com")
                .firstName("randy").lastName("doe").password("randypass")
                .build();
        User randyUser2 = User.builder()
                .userName("randyDoe2").email("randy2Doe@gmail.com")
                .firstName("randy").lastName("doe").password("randypass")
                .build();

        //saving project before saving user will throw an error
        userRepository.save(randyUser1);
        userRepository.save(randyUser2);

        //action - save user with multiple projects
        project1.getUsers().add(randyUser1);
        project1.getUsers().add(randyUser2);
        projectRepository.save(project1);

    }

    @Test
    public void noCascadeDelete(){
        //when - delete
        projectRepository.deleteById(project2.getId());

        //freeplay should be deleted
        Project project = projectRepository.findById(project2.getId()).orElse(null);

        //assertion - project should be null
        assertNull(project);
        //janeDoe projects should be null
        User user = userRepository.findById(janeDoe.getId()).orElse(null);
        assertNotNull(user);
    }

}