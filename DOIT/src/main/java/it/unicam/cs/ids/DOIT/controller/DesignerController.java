package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.role.DesignerRole;
import it.unicam.cs.ids.DOIT.service.DesignerService;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.role.CVUnit;
import it.unicam.cs.ids.DOIT.model.role.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designer")
public class DesignerController {
    @Autowired
    private DesignerService designerMVC;

    //TODO loop
    @GetMapping("/list-projects-by-category")
    public List<Project> listProjectsByCategory(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                                @RequestParam String idcategory) {
        try {
            return List.copyOf(this.designerMVC.listProjectsByCategory(iduser, tokenuser, idcategory));
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/send-pr")
    public String sendPr(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            designerMVC.sendPR(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    //TODO LOOP
    @GetMapping(value = "/list-pr")
    public List<PartecipationRequest<DesignerRole>> listPR(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.designerMVC.listPR(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/list-evaluations")
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
}
