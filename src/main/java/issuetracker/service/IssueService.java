package issuetracker.service;

import issuetracker.entity.Issue;

import java.util.List;

public interface IssueService {
	List<Issue> getIssueList();

	void addIssue(Issue issue);
}
