package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.UserMVC;
import it.unicam.cs.ids.DOIT.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserMVC userMVC;

    @GetMapping(value = "/get")
    public User getUser(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        User user = this.userMVC.getUser(iduser, tokenuser);
        return user;
    }

    @GetMapping(value = "/login")
    public String logIn(@RequestParam String email, @RequestParam String password) {
        User user = this.userMVC.logIn(email, password);
        return user.getId() + " " + user.getToken().getToken();
    }

    @GetMapping(value = "/logout")
    public String logOut(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        this.userMVC.logOut(iduser, tokenuser);
        return "success";
    }

    @GetMapping(value = "/signin")
    public String signIn(@RequestParam String name, @RequestParam String surname, @RequestParam String birthdate,
                         @RequestParam String sex, @RequestParam String email, @RequestParam String password) {
        this.userMVC.signIn(name, surname, birthdate, sex, email, password);
        return "success";
    }

    @GetMapping(value = "/add-role")
    public String addRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole,
                          @RequestParam String idcategory) {
        this.userMVC.addRole(iduser, tokenuser, idrole, idcategory);
        return "success";
    }

    @GetMapping(value = "/remove-role")
    public String removeRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole) {
        this.userMVC.removeRole(iduser, tokenuser, idrole);
        return "success";
    }

    @GetMapping(value = "/get-roles")
    public List<String> getRoles() {
        return this.userMVC.getRoles();
    }

}
