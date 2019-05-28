package issuetracker.controller;

import issuetracker.entity.Role;
import issuetracker.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final String VIEW_SHOW_ADD_ROLE_FORM = "roles/showAddOrUpdate";

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/list")
    public String listRoles(Model model) {
        Set<Role> rolesList = roleService.findAll();
        model.addAttribute("roles", rolesList);
        return "roles/home";
    }

    @GetMapping("/new")
    public String showRoleAddForm(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return VIEW_SHOW_ADD_ROLE_FORM;
    }

    @PostMapping("/new")
    public String addRole(@ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:/roles/list";
    }

    @GetMapping("/edit/{roleId}")
    public String showUpdateForm(@PathVariable("roleId") int roleId, Model model) {
        Role role = roleService.findById(roleId);
        model.addAttribute("role", role);
        return VIEW_SHOW_ADD_ROLE_FORM;
    }

    @DeleteMapping("/delete/{roleId}")
    public String deleteRole(@PathVariable("roleId") int roleId) {
        roleService.deleteById(roleId);
        return "redirect:/roles/list";
    }

}
