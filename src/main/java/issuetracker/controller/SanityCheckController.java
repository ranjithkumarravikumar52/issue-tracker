package issuetracker.controller;

import issuetracker.config.DBCheckConfig;
import issuetracker.sanitycheck.SanityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sanityCheck")
public class SanityCheckController {

	@Autowired
	private SanityCheckService sanityCheckService;

	@Autowired
	private DBCheckConfig dbCheckConfig;

	@GetMapping("/dbConnection")
	public String showDBCheck(Model model){
		model.addAttribute("sanity", sanityCheckService.sanityDBCheck(dbCheckConfig));
		return "db-sanity-check";
	}
}
