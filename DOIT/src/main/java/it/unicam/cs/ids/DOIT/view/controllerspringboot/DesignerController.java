package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.controller.DesignerMVC;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.role.CVUnit;
import it.unicam.cs.ids.DOIT.role.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designer")
public class DesignerController {
    @Autowired
    private DesignerMVC designerMVC;

    @GetMapping("/list-projects-by-category")
    public List<Project> listProjectsByCategory(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                                @RequestParam String idCategory) {
        try {
            return List.copyOf(this.designerMVC.listProjectsByCategory(iduser, tokenuser, idCategory));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/send-pr")
    public String sendPr(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            designerMVC.sendPR(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/list-evalutations")
    public List<Evaluation> listEvaluations(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.designerMVC.listEvaluations(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/list-cv")
    public List<CVUnit> listCV(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.designerMVC.listCV(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-history")
    public List<Project> listHistory(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.designerMVC.listHistory(iduser, tokenuser));
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
