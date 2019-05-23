package issuetracker.service.springdatajpa;

import issuetracker.entity.Issue;
import issuetracker.repository.IssueRepository;
import issuetracker.service.IssueService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IssueServiceSDJPAImpl implements IssueService {

	private final IssueRepository issueRepository;

    public IssueServiceSDJPAImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public Set<Issue> findAll() {
        Set<Issue> issues = new HashSet<>();
        issueRepository.findAll().forEach(issues :: add);
        return issues;
    }

    @Override
    public Issue findById(Integer integer) {
        return issueRepository.findById(integer).orElse(null);
    }

    @Override
    public Issue save(Issue object) {
        return issueRepository.save(object);
    }

    @Override
    public void delete(Issue object) {
        issueRepository.delete(object);
    }

    @Override
    public void deleteById(Integer integer) {
        issueRepository.deleteById(integer);
    }
}
