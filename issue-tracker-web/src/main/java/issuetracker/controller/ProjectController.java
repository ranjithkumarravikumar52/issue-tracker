package issuetracker.controller;

import issuetracker.entity.Project;
import issuetracker.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final String VIEW_SHOW_ADD_PROJECT_FORM = "projects/showAddOrUpdate";

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
	public String listProjects(Model model) {
        Set<Project> projects = projectService.findAll();
		model.addAttribute("projects", projects);
        return "projects/home";
	}

	@GetMapping("/new")
	public String showProjectAddForm(Model model){
		Project project = new Project();
		model.addAttribute("project", project);
        return VIEW_SHOW_ADD_PROJECT_FORM;
	}

	@PostMapping("/new")
	public String addProject(@ModelAttribute ("project") Project project){
        projectService.save(project);
        return "redirect:/projects/list";
	}

	@GetMapping("/edit/{projectId}")
	public String showUpdateForm(@PathVariable("projectId") int projectId, Model model){
        Project project = projectService.findById(projectId);
		model.addAttribute("project", project);
        return VIEW_SHOW_ADD_PROJECT_FORM;
	}

	@GetMapping("/delete/{projectId}")
	public String deleteProject(@PathVariable("projectId") int projectId){
        projectService.deleteById(projectId);
        return "redirect:/projects/list";
    }

    @GetMapping("/{projectId}")
    public String showInDetailForm(@PathVariable("projectId") int projectId, Model model){
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("issues", projectService.findAllIssuesByProjectId(projectId));
        return "projects/inDetail";
    }

    //TODO Chain different ids probably using initBinder
	/*@GetMapping("/projectUserList")
	public String showProjectUserForm(Model model){
		List<Project> projects = projectService.listProjects();
		model.addAttribute("projects", projects);
		return "show-projects-users-form";
	}*/
}
