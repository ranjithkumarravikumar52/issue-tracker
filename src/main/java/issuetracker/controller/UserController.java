package issuetracker.controller;

import issuetracker.entity.User;
import issuetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
