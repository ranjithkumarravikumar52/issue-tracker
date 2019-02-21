package issuetracker.controller;

import issuetracker.entity.Issue;
import issuetracker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
