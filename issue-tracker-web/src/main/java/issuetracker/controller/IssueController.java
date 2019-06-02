package issuetracker.controller;

import issuetracker.entity.Issue;
import issuetracker.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getIssueList(Model model) {
        model.addAttribute("issues", issueService.findAll());
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
}
