package issuetracker.bootstrap;

import issuetracker.entity.Project;
import issuetracker.entity.Role;
import issuetracker.service.IssueService;
import issuetracker.service.ProjectService;
import issuetracker.service.RoleService;
import issuetracker.service.UserService;

import java.util.Arrays;

public abstract class AbstractDataLoader {
    UserService userService;
    IssueService issueService;
    ProjectService projectService;
    RoleService roleService;

    Project mobileApp;
    Project machineLearningDemo;
    Project nlpProject;

    Role admin;
    Role developer;
    Role tester;
    Role sales;
    Role humanResources;

    void initProjectsData() {
        //init 3 projects
        mobileApp = Project.builder()
                .title("mobile app")
                .projectDescription("An android mobile application")
                .build();
        machineLearningDemo = Project.builder()
                .title("machine learning demo")
                .projectDescription("Demo project to practice introduction to machine learning course")
                .build();
        nlpProject = Project.builder()
                .title("nlp assignment")
                .projectDescription("Social media sentiment analysis")
                .build();

        //save all the 3 projects
        projectService.saveAll(Arrays.asList(mobileApp, machineLearningDemo, nlpProject));
    }

    void initRolesData() {
        //init 5 roles
        developer = Role.builder()
                .name("developer")
                .build();
        tester = Role.builder()
                .name("tester")
                .build();
        admin = Role.builder()
                .name("admin")
                .build();
        sales = Role.builder()
                .name("sales")
                .build();
        humanResources = Role.builder()
                .name("human resources")
                .build();

        //save all 5 roles
        roleService.saveAll(Arrays.asList(developer, tester, admin, sales, humanResources));

    }
}
