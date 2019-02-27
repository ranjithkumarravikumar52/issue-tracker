package issuetracker.controller;

import issuetracker.entity.Role;
import issuetracker.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;


	@GetMapping("/list")
	public String listRoles(Model model) {
		List<Role> rolesList = roleService.getRolesList();
		model.addAttribute("roles", rolesList);
		return "list-roles";
	}

	@GetMapping("/showAddForm")
	public String showRoleAddForm(Model model) {
		Role role = new Role();
		model.addAttribute("role", role);
		return "show-add-role-form";
	}

	@PostMapping("/addRole")
	public String addRole(@ModelAttribute("role") Role role) {
		roleService.addRole(role);
		return "redirect:/role/list";
	}

	@GetMapping("/showUpdateForm")
	public String showUpdateForm(@RequestParam("roleId") int roleId, Model model) {
		Role role = roleService.getRole(roleId);
		model.addAttribute("role", role);
		return "show-add-role-form";
	}

	@GetMapping("/delete")
	public String deleteRole(@RequestParam("roleId") int roleId) {
		roleService.deleteRole(roleId);
		return "redirect:/role/list";
	}

}
