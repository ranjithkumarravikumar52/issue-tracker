package issuetracker.service;

import issuetracker.entity.Project;

import java.util.List;

public interface ProjectService {
	List<Project> listProjects();

	void addProject(Project project);
}
