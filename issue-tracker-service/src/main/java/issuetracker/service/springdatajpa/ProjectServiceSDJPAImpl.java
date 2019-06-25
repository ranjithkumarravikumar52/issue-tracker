package issuetracker.service.springdatajpa;

import issuetracker.entity.Issue;
import issuetracker.entity.Project;
import issuetracker.entity.User;
import issuetracker.repository.ProjectRepository;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectServiceSDJPAImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private IssueService issueService;
    private UserService userService;

    public ProjectServiceSDJPAImpl(ProjectRepository projectRepository, IssueService issueService, UserService userService) {
        this.projectRepository = projectRepository;
        this.issueService = issueService;
        this.userService = userService;
    }


    @Override
    public Set<Project> findAll() {
        Set<Project> projects = new LinkedHashSet<>();
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

    @Override
    public Iterable<Project> saveAll(Iterable<Project> entities) {
        return projectRepository.saveAll(entities);
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

    @Override
    public List<User> findAllUsersByProjectId(int projectId) {
        List<Object[]> allUsersByProjectId = projectRepository.findAllUsersByProjectId(projectId);

        if(allUsersByProjectId == null || allUsersByProjectId.size() == 0)
            return null;

        List<User> userList = new ArrayList<>();
        for(Object[] objects: allUsersByProjectId){
            userList.add(userService.findById((Integer) objects[0]));
        }
        return userList;
    }
}
