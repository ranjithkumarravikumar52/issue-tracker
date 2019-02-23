package issuetracker.controller;

import issuetracker.entity.Project;
import issuetracker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

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

	@GetMapping("/showAddForm")
	public String showProjectAddForm(Model model){
		Project project = new Project();
		model.addAttribute("project", project);
		return "show-add-project-form";
	}
	@PostMapping("/addProject")
	public String addProject(@ModelAttribute ("project") Project project){
		projectService.addProject(project);
		return "redirect:/project/list";
	}
	@GetMapping("/showUpdateForm")
	public String showUpdateForm(@RequestParam("projectId") int projectId, Model model){
		Project project = projectService.getProject(projectId);
		model.addAttribute("project", project);
		return "show-add-project-form";
	}
}
