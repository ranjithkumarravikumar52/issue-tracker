package issuetracker.service.springdatajpa;

import issuetracker.entity.Issue;
import issuetracker.entity.Project;
import issuetracker.repository.ProjectRepository;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class ProjectServiceSDJPAImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private IssueService issueService;

    public ProjectServiceSDJPAImpl(ProjectRepository projectRepository, IssueService issueService) {
        this.projectRepository = projectRepository;
        this.issueService = issueService;
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

    /**
     * find all issues(postedBy) in a given project
     * @param projectId - given project
     * @return - all issues in that project
     */
    @Override
    public List<Issue> findAllIssuesByProjectId(int projectId) {
        List<Object[]> allIssuesByProjectId = projectRepository.findAllIssuesByProjectId(projectId);

        if(allIssuesByProjectId == null || allIssuesByProjectId.size() == 0){
            return null;
        }

        List<Issue> issuesList = new ArrayList<>();
        for(Object[] objects: allIssuesByProjectId){
            issuesList.add(issueService.findById((Integer) objects[0]));
        }
        return issuesList;
    }
}
