package issuetracker.controller;

import issuetracker.entity.User;
import issuetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final String VIEW_SHOW_ADD_USER_FORM = "show-add-user-form";
    private final String VIEW_LIST_ALL_USERS = "list-users";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/listUsers")
    public String listUsers(Model model) {
        Set<User> users = userService.findAll();
        model.addAttribute("users", users);
        return VIEW_LIST_ALL_USERS;
    }

    @GetMapping("/showAddForm")
    public String showAddForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return VIEW_SHOW_ADD_USER_FORM;
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_SHOW_ADD_USER_FORM;
        } else {
            userService.save(user);
            return "redirect:/listUsers";
        }
    }

    @GetMapping("/showUpdateForm")
    public String showUpdateForm(@RequestParam("userId") int userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return VIEW_SHOW_ADD_USER_FORM;
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") int userId) {
        userService.deleteById(userId);
        return "redirect:/listUsers";
    }
}
