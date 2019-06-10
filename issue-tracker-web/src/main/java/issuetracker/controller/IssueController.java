package issuetracker.controller;

import issuetracker.entity.Issue;
import issuetracker.service.IssueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private static final String VIEW_SHOW_ADD_OR_UPDATE_ISSUE_FORM = "issues/showAddOrUpdate";
    private static final String VIEW_LIST_ALL_ISSUES = "issues/home";

    private final IssueService issueService;
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    /**
     * List all issues
     */
    @GetMapping("/list")
    public String getIssueList(Model model, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Issue> issues = issueService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        int totalPages = issues.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        model.addAttribute("issues", issues);
        return VIEW_LIST_ALL_ISSUES;
    }

    /**
     * Show form for saving new issue
     */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("issue", new Issue());
        return VIEW_SHOW_ADD_OR_UPDATE_ISSUE_FORM;
    }

    /**
     * Post mapping to save new issue
     */
    @PostMapping("/new")
    public String addIssue(@ModelAttribute("issue") Issue issue) {
        issueService.save(issue);
        return "redirect:/issues/list";
    }

    /**
     * Show form for updating an issue
     */
    @GetMapping("/edit/{issueId}")
    public String showUpdateForm(@PathVariable("issueId") int issueId, Model model) {
        Issue issue = issueService.findById(issueId);
        model.addAttribute(issue);
        return VIEW_SHOW_ADD_OR_UPDATE_ISSUE_FORM;
    }

    /**
     * Delete an issue
     */
    @GetMapping("/delete/{issueId}")
    public String deleteIssue(@PathVariable("issueId") int issueId) {
        issueService.deleteById(issueId);
        return "redirect:/issues/list";
    }

    @GetMapping("/{issueId}")
    public String showInDetailForm(@PathVariable("issueId") int issueId, Model model){
        model.addAttribute("issue", issueService.findById(issueId));
        return "issues/inDetail";
    }
}
