package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.service.ProgramManagerService;
import it.unicam.cs.ids.DOIT.model.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.role.DesignerRole;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/program-manager")
public class ProgramManagerController {
    @Autowired
    private ProgramManagerService programManagerService;

    @GetMapping("/list-projects-by-category")
    public List<Project> listProjectsByCategory(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                                @RequestParam String idcategory) {
        try {
            return List.copyOf(this.programManagerService.listProjectsByCategory(iduser, tokenuser, idcategory));
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/send-pr")
    public String sendPr(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            programManagerService.sendPR(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-pr")
    public List<PartecipationRequest<ProgramManagerRole>> listPR(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.programManagerService.listPR(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/open-registrations")
    public String openRegistrations(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            this.programManagerService.openRegistrations(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping(value = "/close-registrations")
    public String closeRegistrations(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            this.programManagerService.closeRegistrations(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-designer-pr")
    public List<PartecipationRequest<DesignerRole>> listDesignerPR(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                                                   @RequestParam Long idproject) {
        try {
            return List.copyOf(this.programManagerService.listDesignerPR(iduser, tokenuser, idproject));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/accept-designer-pr")
    public String addDesigner(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long iddesignerpr) {
        try {
            this.programManagerService.acceptDesignerPR(iduser, tokenuser, iddesignerpr);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping(value = "/remove-designer-pr")
    public String removePr(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long iddesignerpr,
                           @RequestParam String reason) {
        try {
            this.programManagerService.removeDesignerPR(iduser, tokenuser, iddesignerpr, reason);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping(value = "/remove-designer")
    public String removeDesigner(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                 @RequestParam Long iddesigner, @RequestParam Long idproject) {
        try {
            this.programManagerService.removeDesigner(iduser, tokenuser, iddesigner, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-designers")
    public List<User> getDesigners(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                   @RequestParam Long idproject) {
        try {
            return List.copyOf(this.programManagerService.listDesigners(iduser, tokenuser, idproject));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/set-project-manager")
    public String setProjectManager(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                    @RequestParam Long iddesigner, @RequestParam Long idproject) {
        try {
            this.programManagerService.setProjectManager(iduser, tokenuser, iddesigner, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-history")
    public List<Project> listHistory(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.programManagerService.listHistory(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-projects")
    public List<Project> listProjects(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.programManagerService.listProjects(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-categories")
    public List<Category> listCategories(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.programManagerService.listCategories(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }
}
