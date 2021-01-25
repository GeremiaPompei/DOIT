package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.ProjectProposerMVC;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-proposer")
public class ProjectProposerController {
    @Autowired
    private ProjectProposerMVC projectProposerMVC;

    @GetMapping(value = "/create-project")
    public String createProject(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String name,
                                @RequestParam String description, @RequestParam String idcategory) {
        try {
            this.projectProposerMVC.createProject(iduser, tokenuser, name, description, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-pr")
    public List<PartecipationRequest<ProgramManagerRole>> getProgramManagerPR(@RequestParam Long iduser,
                                                                              @RequestParam Long tokenuser,
                                                                              @RequestParam Long idproject) {
        try {
            return List.copyOf(this.projectProposerMVC.listPR(iduser, tokenuser, idproject));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/accept-pr")
    public String acceptAcceptProgramManagerPR(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                               @RequestParam Long idprogrammanager, @RequestParam Long idproject) {
        try {
            this.projectProposerMVC.acceptPR(iduser, tokenuser, idprogrammanager, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/remove-pr")
    public String removeAcceptProgramManagerPR(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                               @RequestParam Long idprogrammanager, @RequestParam Long idproject,
                                               @RequestParam String reason) {
        try {
            this.projectProposerMVC.removePR(iduser, tokenuser, idprogrammanager, idproject, reason);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
