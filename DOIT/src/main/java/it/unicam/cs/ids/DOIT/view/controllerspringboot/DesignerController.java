package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.DesignerMVC;
import it.unicam.cs.ids.DOIT.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designer")
public class DesignerController {
    @Autowired
    private DesignerMVC designerMVC;

    @GetMapping("/send-pr")
    public String sendPr(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            designerMVC.sendPR(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/list-projects")
    public List<Project> listProjects(@RequestBody String argv) {
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/visualize-evalutation")
    public Integer visualizeEvaluation(@RequestBody String argv) {
        try {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
