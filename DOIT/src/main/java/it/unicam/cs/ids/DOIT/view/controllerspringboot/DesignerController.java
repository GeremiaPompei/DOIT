package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.category.Category;
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

    @GetMapping("/list-projects-by-category")
    public List<Project> listProjectsByCategory(@RequestBody String argv) {
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

    @GetMapping(value = "/list-projects")
    public List<Project> listProjects(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.designerMVC.listProjects(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-categories")
    public List<Category> listCategories(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.designerMVC.listCategories(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/add-category")
    public String addCategory(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idcategory) {
        try {
            this.designerMVC.addCategory(iduser, tokenuser, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/remove-category")
    public String removeCategory(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idcategory) {
        try {
            this.designerMVC.removeCategory(iduser, tokenuser, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
