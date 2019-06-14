package issuetracker.repository;

import issuetracker.entity.Issue;
import issuetracker.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface IssueRepository extends CrudRepository<Issue, Integer> {
    Set<Issue> findAllByOpenedBy(User openedByUser);

    Set<Issue> findAllByClosedBy(User closedByUser);
}
