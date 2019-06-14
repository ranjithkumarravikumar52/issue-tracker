package issuetracker.service.springdatajpa;

import issuetracker.entity.Issue;
import issuetracker.entity.IssueStatus;
import issuetracker.entity.User;
import issuetracker.repository.IssueRepository;
import issuetracker.service.IssueService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("springdatajpa")
public class IssueServiceSDJPAImpl implements IssueService {

	private final IssueRepository issueRepository;

    public IssueServiceSDJPAImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public Set<Issue> findAll() {
        Set<Issue> issues = new HashSet<>();
        issueRepository.findAll().forEach(issues :: add);
        return issues;
    }

    @Override
    public Issue findById(Integer integer) {
        return issueRepository.findById(integer).orElse(null);
    }

    @Override
    public Issue save(Issue object) {
        if(object.getIssueStatus() == null){
            object.setIssueStatus(IssueStatus.OPEN);
        }
        return issueRepository.save(object);
    }

    @Override
    public void delete(Issue object) {
        issueRepository.delete(object);
    }

    @Override
    public void deleteById(Integer integer) {
        issueRepository.deleteById(integer);
    }

    @Override
    public Page<Issue> findPaginated(Pageable pageable) {
        List<Issue> allIssues = new ArrayList<>(this.findAll());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Issue> pageIssues;

        if(allIssues.size() < startItem){
            pageIssues = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem + pageSize, allIssues.size());
            pageIssues = allIssues.subList(startItem, toIndex);
        }

        return new PageImpl<>(pageIssues, PageRequest.of(currentPage, pageSize), allIssues.size());
    }

    @Override
    public Set<Issue> findAllByOpenedBy(User openedByUser) {
        return issueRepository.findAllByOpenedBy(openedByUser);
    }

    @Override
    public Set<Issue> findAllByClosedBy(User closedByUser) {
        return issueRepository.findAllByClosedBy(closedByUser);
    }

}
