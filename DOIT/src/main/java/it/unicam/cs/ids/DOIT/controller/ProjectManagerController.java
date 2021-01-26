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
    private ProjectManagerService projectManagerMVC;

    @GetMapping(value = "/upgrade-state")
    public String ugradeState(@RequestParam Long iduser, @RequestParam Long tokenuser,
                              @RequestParam Long idproject) {
        try {
            this.projectManagerMVC.upgradeState(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/downgrade-state")
    public String downgradeState(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                 @RequestParam Long idproject) {
        try {
            this.projectManagerMVC.downgradeState(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-designers")
    public List<User> listDesigners(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                    @RequestParam Long idproject) {
        try {
            return List.copyOf(this.projectManagerMVC.listDesigners(iduser, tokenuser, idproject));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/evaluate")
    public String evaluateDesigner(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                   @RequestParam Long iddesigner, @RequestParam Long idproject,
                                   @RequestParam Integer evaluation) {
        try {
            this.projectManagerMVC.evaluate(iduser, tokenuser, iddesigner, idproject, evaluation);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/exit-all")
    public String exitAll(@RequestParam Long iduser, @RequestParam Long tokenuser,
                          @RequestParam Long idproject) {
        try {
            this.projectManagerMVC.exitAll(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/visualize-state")
    public ProjectState visualizeState(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                       @RequestParam Long idproject) {
        try {
            return this.projectManagerMVC.visualizeState(iduser, tokenuser, idproject);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-history")
    public List<Project> listHistory(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.projectManagerMVC.listHistory(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-projects")
    public List<Project> listProjects(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.projectManagerMVC.listProjects(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-categories")
    public List<Category> listCategories(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.projectManagerMVC.listCategories(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }
}
