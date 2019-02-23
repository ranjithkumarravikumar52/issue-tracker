package issuetracker.controller;

import issuetracker.entity.Project;
import issuetracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	ProjectService projectService;

	@GetMapping("/list")
	public String listProjects(Model model) {
		List<Project> projects = projectService.listProjects();
		model.addAttribute("projects", projects);
		return "list-projects";
	}
}
