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
}
