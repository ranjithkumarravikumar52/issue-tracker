package issuetracker.service;

import issuetracker.entity.Issue;

import java.util.List;

public interface IssueService {
	List<Issue> getIssueList();

	void addIssue(Issue issue);

	Issue getIssue(int issueId);

	void deleteIssue(int issueId);
}
