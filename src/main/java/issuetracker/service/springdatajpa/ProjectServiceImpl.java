package issuetracker.service.springdatajpa;

import issuetracker.repository.ProjectRepository;
import issuetracker.entity.Project;
import issuetracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	@Transactional
	public List<Project> listProjects() {
		return projectRepository.listProjects();
	}

	@Override
	@Transactional
	public void addProject(Project project) {
		projectRepository.addProject(project);
	}

	@Override
	@Transactional
	public Project getProject(int projectId) {
		return projectRepository.getProject(projectId);
	}

	@Override
	@Transactional
	public void delete(int projectId) {
		projectRepository.delete(projectId);
	}
}
