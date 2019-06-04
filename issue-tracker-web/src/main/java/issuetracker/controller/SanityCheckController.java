package issuetracker.controller;

import issuetracker.config.DBCheckConfig;
import issuetracker.sanitycheck.SanityCheckService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sanityCheck")
public class SanityCheckController {

    private SanityCheckService sanityCheckService;
    private DBCheckConfig dbCheckConfig;

    public SanityCheckController(SanityCheckService sanityCheckService, DBCheckConfig dbCheckConfig) {
        this.sanityCheckService = sanityCheckService;
        this.dbCheckConfig = dbCheckConfig;
    }

    @GetMapping("/dbConnection")
    public String showDBCheck(Model model) {
        model.addAttribute("sanity", sanityCheckService.sanityDBCheck(dbCheckConfig));
        return "sanitycheck/dbSanityCheck";
    }
}
