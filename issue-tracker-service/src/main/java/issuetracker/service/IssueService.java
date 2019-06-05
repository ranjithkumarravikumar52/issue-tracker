package issuetracker.service;

import issuetracker.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueService extends CrudService<Issue, Integer>{
    Page<Issue> findPaginated(Pageable pageable);
}
