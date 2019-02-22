package issuetracker.controller;

import issuetracker.entity.Issue;
import issuetracker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/issue")
public class IssueController {

	@Autowired
	private IssueService issueService;

	@RequestMapping("/issueList")
	public String getissueList(Model model){
		List<Issue> issueList = issueService.getIssueList();
		model.addAttribute("issues", issueList);
		return "list-issues";
	}

	/**
	 * For adding new issue
	 * @param model
	 * @return
	 */
	@RequestMapping("/showAddForm")
	public String showAddForm(Model model){
		Issue issue = new Issue();
		model.addAttribute("issue", issue);
		return "show-add-issue-form";
	}
	@PostMapping("/addIssue")
	public String addIssue(@ModelAttribute("issue") Issue issue){
		issueService.addIssue(issue);
		return "redirect:/issue/issueList";
	}
}
