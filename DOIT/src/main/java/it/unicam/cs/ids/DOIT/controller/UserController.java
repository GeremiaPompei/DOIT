package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.service.UserService;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userMVC;

    @GetMapping(value = "/get")
    public User getUser(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            User user = this.userMVC.getUser(iduser, tokenuser);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/login")
    public String logIn(@RequestParam String email, @RequestParam String password) {
        try {
            User user = this.userMVC.logIn(email, password);
            return user.getId() + " " + user.getTokenHandler().getToken();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/logout")
    public String logOut(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            this.userMVC.logOut(iduser, tokenuser);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/signin")
    public String signIn(@RequestParam String name, @RequestParam String surname, @RequestParam String birthdate,
                         @RequestParam String sex, @RequestParam String email, @RequestParam String password) {
        try {
            this.userMVC.signIn(name, surname, birthdate, sex, email, password);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/add-role")
    public String addRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole,
                          @RequestParam String idcategory) {
        try {
            this.userMVC.addRole(iduser, tokenuser, idrole, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/remove-role")
    public String removeRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole) {
        try {
            this.userMVC.removeRole(iduser, tokenuser, idrole);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/get-roles")
    public List<String> getRoles() {
        try {
            return this.userMVC.getRoles();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/add-category-to-role")
    public String addCategoryToRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole,
                                    @RequestParam String idcategory) {
        try {
            this.userMVC.addCategory(iduser, tokenuser, idrole, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/remove-category-to-role")
    public String removeCategoryToRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole,
                                       @RequestParam String idcategory) {
        try {
            this.userMVC.removeCategory(iduser, tokenuser, idrole, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
