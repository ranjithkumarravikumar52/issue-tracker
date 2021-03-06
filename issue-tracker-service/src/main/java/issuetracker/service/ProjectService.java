package issuetracker.service;

import issuetracker.entity.Issue;
import issuetracker.entity.Project;
import issuetracker.entity.User;

import java.util.List;

public interface ProjectService extends CrudService<Project, Integer>{
    List<Issue> findAllIssuesByProjectId(int projectId);
    List<User> findAllUsersByProjectId(int projectId);
}
