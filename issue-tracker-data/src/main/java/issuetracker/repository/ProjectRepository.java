package issuetracker.repository;

import issuetracker.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
    @Query(value = "SELECT i.*\n" +
            "FROM issue i,\n" +
            "     (SELECT user.*\n" +
            "      from user,\n" +
            "           project,\n" +
            "           project_user\n" +
            "      where user.id = project_user.user_id\n" +
            "        and project.id = project_user.project_id\n" +
            "        and project.id = ?1) e\n" +
            "WHERE i.posted_by_id = e.id"
            , nativeQuery = true)
    List<Object[]> findAllIssuesByProjectId(int projectId);

    @Query(value = "SELECT user.*\n" +
            "from user,\n" +
            "     project,\n" +
            "     project_user\n" +
            "where user.id = project_user.user_id\n" +
            "  and project.id = project_user.project_id\n" +
            "  and project.id = ?1"
            , nativeQuery = true)
    List<Object[]> findAllUsersByProjectId(int projectId);


}
