package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.controller.ProgramManagerMVC;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/program-manager")
public class ProgramManagerController {
    @Autowired
    private ProgramManagerMVC programManagerMVC;

    @GetMapping("/list-projects-by-category")
    public List<Project> listProjectsByCategory(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                                @RequestParam String idcategory) {
        try {
            return List.copyOf(this.programManagerMVC.listProjectsByCategory(iduser, tokenuser, idcategory));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/send-pr")
    public String sendPr(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            programManagerMVC.sendPR(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-pr")
    public List<PartecipationRequest<ProgramManagerRole>> listPR(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.programManagerMVC.listPR(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/open-registrations")
    public String openRegistrations(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            this.programManagerMVC.openRegistrations(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/close-registrations")
    public String closeRegistrations(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            this.programManagerMVC.closeRegistrations(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-designer-pr")
    public List<PartecipationRequest<DesignerRole>> listDesignerPR(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                                                   @RequestParam Long idproject) {
        try {
            return List.copyOf(this.programManagerMVC.listDesignerPR(iduser, tokenuser, idproject));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/accept-designer-pr")
    public String addDesigner(@RequestParam Long iduser, @RequestParam Long tokenuser,
                              @RequestParam Long iddesigner, @RequestParam Long idproject) {
        try {
            this.programManagerMVC.acceptDesignerPR(iduser, tokenuser, iddesigner, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/remove-designer-pr")
    public String removePr(@RequestParam Long iduser, @RequestParam Long tokenuser,
                           @RequestParam Long iddesigner, @RequestParam Long idproject,
                           @RequestParam String reason) {
        try {
            this.programManagerMVC.removeDesignerPR(iduser, tokenuser, iddesigner, idproject, reason);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/remove-designer")
    public String removeDesigner(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                 @RequestParam Long iddesigner, @RequestParam Long idproject) {
        try {
            this.programManagerMVC.removeDesigner(iduser, tokenuser, iddesigner, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-designers")
    public List<User> getDesigners(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                   @RequestParam Long idproject) {
        try {
            return List.copyOf(this.programManagerMVC.listDesigners(iduser, tokenuser, idproject));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/set-project-manager")
    public String setProjectManager(@RequestParam Long iduser, @RequestParam Long tokenuser,
                                    @RequestParam Long iddesigner, @RequestParam Long idproject) {
        try {
            this.programManagerMVC.setProjectManager(iduser, tokenuser, iddesigner, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-history")
    public List<Project> listHistory(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.programManagerMVC.listHistory(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-projects")
    public List<Project> listProjects(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.programManagerMVC.listProjects(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-categories")
    public List<Category> listCategories(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return List.copyOf(this.programManagerMVC.listCategories(iduser, tokenuser));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/add-category")
    public String addCategory(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idcategory) {
        try {
            this.programManagerMVC.addCategory(iduser, tokenuser, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/remove-category")
    public String removeCategory(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idcategory) {
        try {
            this.programManagerMVC.removeCategory(iduser, tokenuser, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
