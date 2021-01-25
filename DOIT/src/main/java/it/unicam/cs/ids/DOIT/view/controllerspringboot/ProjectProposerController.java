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
        this.projectProposerMVC.createProject(iduser, tokenuser, name, description, idcategory);
        return "success";
    }

    @GetMapping(value = "/program-manager-pr")
    public List<PartecipationRequest<ProgramManagerRole>> getProgramManagerPR(@RequestParam Long iduser,
                                                                              @RequestParam Long tokenuser,
                                                                              @RequestParam Long idproject) {
        return null;
    }

    @GetMapping(value = "/accept-program-manager-pr")
    public void acceptAcceptProgramManagerPR(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                             @RequestParam Long idprgrammanager, @RequestParam Long idproject) {
    }

    @GetMapping(value = "/remove-program-manager-pr")
    public void removeAcceptProgramManagerPR(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                             @RequestParam Long idprgrammanager, @RequestParam Long idproject,
                                             @RequestParam String reason) {
    }
}
