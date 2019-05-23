package issuetracker.service.springdatajpa;

import issuetracker.entity.Project;
import issuetracker.repository.ProjectRepository;
import issuetracker.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceSDJPAImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceSDJPAImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

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
