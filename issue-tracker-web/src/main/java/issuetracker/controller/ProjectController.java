package issuetracker.controller;

import issuetracker.entity.Project;
import issuetracker.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final String VIEW_LIST_PROJECTS = "list-projects";
    private final String VIEW_SHOW_ADD_PROJECT_FORM = "show-add-project-form";

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/listProjects")
	public String listProjects(Model model) {
        Set<Project> projects = projectService.findAll();
		model.addAttribute("projects", projects);
        return VIEW_LIST_PROJECTS;
	}

	@GetMapping("/showAddForm")
	public String showProjectAddForm(Model model){
		Project project = new Project();
		model.addAttribute("project", project);
        return VIEW_SHOW_ADD_PROJECT_FORM;
	}

	@PostMapping("/addProject")
	public String addProject(@ModelAttribute ("project") Project project){
        projectService.save(project);
        return "redirect:/" + VIEW_LIST_PROJECTS;
	}

	@GetMapping("/showUpdateForm")
	public String showUpdateForm(@RequestParam("projectId") int projectId, Model model){
        Project project = projectService.findById(projectId);
		model.addAttribute("project", project);
        return VIEW_SHOW_ADD_PROJECT_FORM;
	}

	@GetMapping("/delete")
	public String deleteProject(@RequestParam("projectId") int projectId){
        projectService.deleteById(projectId);
        return "redirect:/" + VIEW_LIST_PROJECTS;
    }

    //TODO Chain different ids probably using initBinder
	/*@GetMapping("/projectUserList")
	public String showProjectUserForm(Model model){
		List<Project> projects = projectService.listProjects();
		model.addAttribute("projects", projects);
		return "show-projects-users-form";
	}*/
}
