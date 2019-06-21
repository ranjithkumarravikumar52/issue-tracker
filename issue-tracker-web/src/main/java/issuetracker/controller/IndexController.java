package issuetracker.controller;

import issuetracker.entity.User;
import issuetracker.service.AuthenticationFacadeService;
import issuetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController {

    private final AuthenticationFacadeService authenticationFacadeService;
    private final UserService userService;

    @Autowired
    public IndexController(AuthenticationFacadeService authenticationFacadeService, UserService userService) {
        this.authenticationFacadeService = authenticationFacadeService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        String loggedInUserEmailId = authenticationFacadeService.getAuthentication().getName();//this will give us current logged in user email id
        User userByEmail = userService.findUserByEmail(loggedInUserEmailId);
        model.addAttribute("user", userByEmail);
        return "home";
    }

    @GetMapping("/oops")
    public String showNothingImplemented() {
        return "nothingImplemented";
    }
}
