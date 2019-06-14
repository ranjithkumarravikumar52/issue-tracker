package issuetracker.service;

import issuetracker.entity.Issue;
import issuetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IssueService extends CrudService<Issue, Integer>{
    Page<Issue> findPaginated(Pageable pageable);
    Set<Issue> findAllByOpenedBy(User openedByUser);
    Set<Issue> findAllByClosedBy(User closedByUser);
}
