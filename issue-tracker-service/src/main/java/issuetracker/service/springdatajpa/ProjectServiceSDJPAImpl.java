package issuetracker.service.springdatajpa;

import issuetracker.entity.Project;
import issuetracker.repository.ProjectRepository;
import issuetracker.service.ProjectService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class ProjectServiceSDJPAImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceSDJPAImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public Set<Project> findAll() {
        Set<Project> projects = new HashSet<>();
        projectRepository.findAll().forEach(projects :: add);
        return projects;
    }

    @Override
    public Project findById(Integer integer) {
        return projectRepository.findById(integer).orElse(null);
    }

    @Override
    public Project save(Project object) {
        return projectRepository.save(object);
    }

    @Override
    public void delete(Project object) {
        projectRepository.delete(object);
    }

    @Override
    public void deleteById(Integer integer) {
        projectRepository.deleteById(integer);
    }
}
