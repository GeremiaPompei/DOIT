package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.ProjectProposerMVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project-proposer")
public class ProjectProposerController {
    @Autowired
    private ProjectProposerMVC projectProposerMVC;

    @PostMapping(value = "/create-project")
    public String createProject(@RequestBody String args) {
        String[] params = args.split(" ");
        this.projectProposerMVC.createProject(Long.parseLong(params[0]), Long.parseLong(params[1]),
                params[2], params[3], params[4]);
        return "success";
    }
}
