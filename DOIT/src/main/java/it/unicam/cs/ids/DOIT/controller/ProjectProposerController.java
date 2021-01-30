package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.service.ProjectProposerService;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-proposer")
public class ProjectProposerController {
    @Autowired
    private ProjectProposerService projectProposerService;

    @PostMapping(value = "/create-project")
    public String createProject(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String name,
                                @RequestParam String description, @RequestParam String idcategory) {
        try {
            this.projectProposerService.createProject(iduser, tokenuser, name, description, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-other-pr")
    public List<PartecipationRequest<ProgramManagerRole>> listProgramManagerPR(@RequestParam Long iduser,
                                                                               @RequestParam Long tokenuser,
                                                                               @RequestParam Long idproject) {
        try {
            return List.copyOf(this.projectProposerService.listPR(iduser, tokenuser, idproject));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/accept-pr")
    public String acceptProgramManagerPR(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idpr) {
        try {
            this.projectProposerService.acceptPR(iduser, tokenuser, idpr);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping(value = "/remove-pr")
    public String removeProgramManagerPR(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idpr
            , @RequestParam String reason) {
        try {
            this.projectProposerService.removePR(iduser, tokenuser, idpr, reason);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping(value = "/remove-project")
    public String removeProject(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                @RequestParam Long idproject){
        try {
            this.projectProposerService.removeProject(iduser, tokenuser, idproject);
            return "success";
        }
        catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
