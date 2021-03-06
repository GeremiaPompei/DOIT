package it.unicam.cs.ids.DOIT.designer;

import it.unicam.cs.ids.DOIT.entity.PartecipationRequest;
import it.unicam.cs.ids.DOIT.entity.role.DesignerRole;
import it.unicam.cs.ids.DOIT.entity.project.Project;
import it.unicam.cs.ids.DOIT.entity.role.CVUnit;
import it.unicam.cs.ids.DOIT.entity.role.Evaluation;
import it.unicam.cs.ids.DOIT.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/designer")
public class DesignerController {
    @Autowired
    private DesignerService designerService;

    @GetMapping("/list-projects-by-category")
    public List<Project> listProjectsByCategory(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                                @RequestParam String idcategory) {
        try {
            List<Project> list = new ArrayList<>();
            this.designerService.listProjectsByCategory(iduser, tokenuser, idcategory).forEach(p -> list.add(p));
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/send-pr")
    public String sendPr(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            designerService.sendPR(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-my-pr")
    public List<PartecipationRequest<DesignerRole>> listPR(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            List<PartecipationRequest<DesignerRole>> list = new ArrayList<>();
            this.designerService.listPR(iduser, tokenuser).forEach(p -> list.add(p));
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/list-evaluations")
    public List<Evaluation> listEvaluations(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            List<Evaluation> list = new ArrayList<>();
            this.designerService.listEvaluations(iduser, tokenuser).forEach(p -> list.add(p));
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping(value = "/remove-project")
    public String removeProject(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            designerService.removeProject(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/visualize-pregress-experiences")
    public List<CVUnit> visualizePregressExperiences(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return designerService.visualizePregressExperiences(iduser, tokenuser);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/insert-pregress-experience")
    public String insertPregressExperience(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                           @RequestParam String experience, @RequestParam String datestart,
                                           @RequestParam String datefinish) {
        try {
            designerService.insertPregressExperience(iduser, tokenuser, experience, datestart, datefinish);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping(value = "/remove-my-pr")
    public String removePR(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idpr) {
        try {
            this.designerService.removePR(iduser, tokenuser, idpr);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
