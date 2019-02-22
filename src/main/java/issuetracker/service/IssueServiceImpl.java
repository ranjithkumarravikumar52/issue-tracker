package issuetracker.service;

import issuetracker.dao.IssueDAO;
import issuetracker.entity.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService{

	@Autowired
	private IssueDAO issueDAO;

	@Override
	@Transactional
	public List<Issue> getIssueList() {
		return issueDAO.getIssues();
	}

	@Override
	@Transactional
	public void addIssue(Issue issue) {
		issueDAO.addIssue(issue);
	}

	@Override
	@Transactional
	public Issue getIssue(int issueId) {
		return issueDAO.getIssue(issueId);
	}

	@Override
	@Transactional
	public void deleteIssue(int issueId) {
		issueDAO.deleteIssue(issueId);
	}
}
