package issuetracker.controller;

import issuetracker.entity.Issue;
import issuetracker.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/issue")
public class IssueController {

    private static final String VIEW_SHOW_ADD_ISSUE_FORM = "show-add-issue-form";
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    /**
     * List all issues
     */
    @RequestMapping("/issueList")
    public String getIssueList(Model model) {
        Set<Issue> issueList = issueService.findAll();
        model.addAttribute("issues", issueList);
        return "list-issues";
    }

    /**
     * Show form for saving new issue
     */
    @GetMapping("/showAddForm")
    public String showAddForm(Model model) {
        Issue issue = new Issue();
        model.addAttribute("issue", issue);
        return VIEW_SHOW_ADD_ISSUE_FORM;
    }

    /**
     * Post mapping to save new issue
     */
    @PostMapping("/addIssue")
    public String addIssue(@ModelAttribute("issue") Issue issue) {
        issueService.save(issue);
        return "redirect:/issue/issueList";
    }

    /**
     * Show form for updating an issue
     */
    @GetMapping("/showUpdateForm")
    public String showUpdateForm(@RequestParam("issueId") int issueId, Model model) {
        Issue issue = issueService.findById(issueId);
        model.addAttribute(issue);
        return VIEW_SHOW_ADD_ISSUE_FORM;
    }

    /**
     * Delete an issue
     */
    @GetMapping("/delete")
    public String deleteIssue(@RequestParam("issueId") int issueId) {
        issueService.deleteById(issueId);
        return "redirect:/issue/issueList";
    }
}
