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

    @GetMapping(value = "/list-pr")
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
    public String acceptProgramManagerPR(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idprogrammanagerpr) {
        try {
            this.projectProposerService.acceptPR(iduser, tokenuser, idprogrammanagerpr);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-history")
    public List<Project> listHistory(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.projectProposerService.listHistory(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-projects")
    public List<Project> listProjects(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.projectProposerService.listProjects(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-categories")
    public List<Category> listCategories(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.projectProposerService.listCategories(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }
}
