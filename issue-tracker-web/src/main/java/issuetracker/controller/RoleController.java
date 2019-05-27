package issuetracker.controller;

import issuetracker.entity.Role;
import issuetracker.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;
    private final String VIEW_LIST_ROLES = "list-roles";
    private final String VIEW_SHOW_ADD_ROLE_FORM = "show-add-role-form";

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/listRoles")
    public String listRoles(Model model) {
        Set<Role> rolesList = roleService.findAll();
        model.addAttribute("roles", rolesList);
        return VIEW_LIST_ROLES;
    }

    @GetMapping("/showAddForm")
    public String showRoleAddForm(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return VIEW_SHOW_ADD_ROLE_FORM;
    }

    @PostMapping("/addRole")
    public String addRole(@ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:/" + VIEW_LIST_ROLES;
    }

    @GetMapping("/showUpdateForm")
    public String showUpdateForm(@RequestParam("roleId") int roleId, Model model) {
        Role role = roleService.findById(roleId);
        model.addAttribute("role", role);
        return VIEW_SHOW_ADD_ROLE_FORM;
    }

    @GetMapping("/delete")
    public String deleteRole(@RequestParam("roleId") int roleId) {
        roleService.deleteById(roleId);
        return "redirect:/" + VIEW_LIST_ROLES;
    }

}
