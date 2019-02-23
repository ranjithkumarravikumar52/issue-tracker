package issuetracker.dao;

import issuetracker.entity.Project;

import java.util.List;

public interface ProjectDAO {
	List<Project> listProjects();

	void addProject(Project project);

	Project getProject(int projectId);

	void delete(int projectId);
}
