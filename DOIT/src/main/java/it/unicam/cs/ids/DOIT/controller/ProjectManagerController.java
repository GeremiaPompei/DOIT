package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.service.ProjectManagerService;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/project-manager")
public class ProjectManagerController {

    @Autowired
    private ProjectManagerService projectManagerService;

    @PutMapping(value = "/upgrade-state")
    public String ugradeState(@RequestParam Long iduser, @RequestParam Long tokenuser,
                              @RequestParam Long idproject) {
        try {
            this.projectManagerService.upgradeState(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping(value = "/downgrade-state")
    public String downgradeState(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                 @RequestParam Long idproject) {
        try {
            this.projectManagerService.downgradeState(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-designers")
    public List<User> listDesigners(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                    @RequestParam Long idproject) {
        try {
            return List.copyOf(this.projectManagerService.listDesigners(iduser, tokenuser, idproject));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/evaluate")
    public String evaluateDesigner(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                   @RequestParam Long iddesigner, @RequestParam Long idproject,
                                   @RequestParam Integer evaluation) {
        try {
            this.projectManagerService.evaluate(iduser, tokenuser, iddesigner, idproject, evaluation);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping(value = "/exit-all")
    public String exitAll(@RequestParam Long iduser, @RequestParam Long tokenuser,
                          @RequestParam Long idproject) {
        try {
            this.projectManagerService.exitAll(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/visualize-state")
    public ProjectState visualizeState(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                       @RequestParam Long idproject) {
        try {
            return this.projectManagerService.visualizeState(iduser, tokenuser, idproject);
        } catch (Exception e) {
            return null;
        }
    }
}
