package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.ProgramManagerMVC;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/program-manager")
public class ProgramManagerController {
    @Autowired
    private ProgramManagerMVC programManagerMVC;

    @GetMapping("/send-pr")
    public String sendPr(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam Long idproject) {
        try {
            programManagerMVC.sendPR(iduser, tokenuser, idproject);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping(value = "/removepr")
    public String removePr(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping(value = "/acceptpr")
    public String addDesigner(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping (value = "/removedesigner")
    public String removeDesigner(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping (value =  "/set-prjmanager")
    public String setProjectManager(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @GetMapping(value =  "/teamsOwned")
    public List<Project> teamsOwned(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping (value = "/openRegistrations")
    public String openRegistrations(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping (value = "/closeRegistrations")
    public String closeRegistrations(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @GetMapping (value = "/pr-list")
    public List<PartecipationRequest> getPartecipationRequests(@RequestBody String argv){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @GetMapping(value = "/designers-list")
    public List<User> getDesigners(@RequestBody String argv) {
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }
}
