package issuetracker.dao;

import issuetracker.entity.Project;

import java.util.List;

public interface ProjectDAO {
	List<Project> listProjects();
}
