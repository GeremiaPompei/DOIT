package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.ProjectManagerMVC;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/project-manager")
public class ProjectManagerController {

    @Autowired
    private ProjectManagerMVC projectManagerMVC;

    @GetMapping(value = "/projectsOwned")
    public List<Project> getProjectsOwned(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/upgrade")
    public String ugradeState(@RequestBody String argv) {
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping (value = "/downgrade")
    public String downgradeState(@RequestBody String argv) {
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/designers-list")
    public List<User> getDesigners(@RequestBody String argv) {
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping (value = "/evaluate")
    public String evaluateDesigner(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping (value = "/exitfromproject")
    public String exitFromProject(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping (value = "/visualizestate")
    public ProjectState visualizeState(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
