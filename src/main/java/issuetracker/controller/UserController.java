package issuetracker.controller;

import issuetracker.entity.User;
import issuetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/listusers")
	public String listUsers(Model model){
		List<User> users = userService.getUsers();
		model.addAttribute("users", users);
		return "list-users";
	}
	@RequestMapping("/showAddForm")
	public String showAddForm(Model model){
		User user = new User();
		model.addAttribute("user", user);
		return "show-add-user-form";
	}
	@PostMapping("/addUser")
	public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "show-add-user-form";
		}else{
			userService.addUser(user);
			return "redirect:/user/listusers";
		}
	}
	@GetMapping("/showUpdateForm")
	public String showUpdateForm(@RequestParam("userId") int userId, Model model){
		User user = userService.getUser(userId);
		model.addAttribute("user", user);
		return "show-add-user-form";
	}
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("userId") int userId){
		//TODO delete user based on userId
		return "redirect:/user/listusers";
	}
}
