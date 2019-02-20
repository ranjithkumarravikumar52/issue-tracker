package issuetracker.controller;

import issuetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	@RequestMapping("/listusers")
	public String listUsers(){

	}
}
