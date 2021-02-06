package it.unicam.cs.ids.DOIT.project_manager;

import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.project.ProjectState;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            List<User> list = new ArrayList<>();
            this.projectManagerService.listDesigners(iduser, tokenuser, idproject).forEach(p -> list.add(p));
            return list;
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

    @DeleteMapping(value = "/remove-project")
    public String removeProject(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                @RequestParam Long idnextprojectmanager, Long idproject){
        try{
            projectManagerService.removeProject(iduser, tokenuser, idnextprojectmanager, idproject);
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
