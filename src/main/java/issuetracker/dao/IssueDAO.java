package issuetracker.dao;

import issuetracker.entity.Issue;

import java.util.List;

public interface IssueDAO {
	List<Issue> getIssues();

	void addIssue(Issue issue);

	Issue getIssue(int issueId);

	void deleteIssue(int issueId);
}
