package issuetracker.service.springdatajpa;

import issuetracker.entity.Issue;
import issuetracker.repository.IssueRepository;
import issuetracker.service.IssueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IssueServiceSDJPAImpl implements IssueService {

	private final IssueRepository issueRepository;

    public IssueServiceSDJPAImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
	@Transactional
	public List<Issue> getIssueList() {
		return issueRepository.getIssues();
	}

	@Override
	@Transactional
	public void addIssue(Issue issue) {
		issueRepository.addIssue(issue);
	}

	@Override
	@Transactional
	public Issue getIssue(int issueId) {
		return issueRepository.getIssue(issueId);
	}

	@Override
	@Transactional
	public void deleteIssue(int issueId) {
		issueRepository.deleteIssue(issueId);
	}
}
