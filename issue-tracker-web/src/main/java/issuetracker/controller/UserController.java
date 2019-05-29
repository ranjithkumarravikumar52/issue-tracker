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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final String VIEW_SHOW_ADD_USER_FORM = "users/showAddOrUpdate";
    private final String VIEW_LIST_ALL_USERS = "users/home";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        Set<User> users = userService.findAll();
        model.addAttribute("users", users);
        return VIEW_LIST_ALL_USERS;
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return VIEW_SHOW_ADD_USER_FORM;
    }

    @PostMapping("/new")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_SHOW_ADD_USER_FORM;
        } else {
            userService.save(user);
            return "redirect:/users/list";
        }
    }

    @GetMapping("/edit/{userId}")
    public String showUpdateForm(@PathVariable("userId") int userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return VIEW_SHOW_ADD_USER_FORM;
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") int userId) {
        userService.deleteById(userId);
        return "redirect:/users/list";
    }
}
