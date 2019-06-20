package issuetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController {
    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/oops")
    public String showNothingImplemented() {
        return "nothingImplemented";
    }
}
