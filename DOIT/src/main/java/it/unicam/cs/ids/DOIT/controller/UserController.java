package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.project.Project;
import it.unicam.cs.ids.DOIT.model.role.Notification;
import it.unicam.cs.ids.DOIT.model.role.RolesHandler;
import it.unicam.cs.ids.DOIT.service.UserService;
import it.unicam.cs.ids.DOIT.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/get")
    public User getUser(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            User user = this.userService.getUser(iduser, tokenuser);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/login")
    public Credential logIn(@RequestParam String email, @RequestParam String password) {
        try {
            User user = this.userService.logIn(email, password);
            return new Credential(user);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/logout")
    public String logOut(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            this.userService.logOut(iduser, tokenuser);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping(value = "/signin")
    public String signIn(@RequestParam String name, @RequestParam String surname, @RequestParam String birthdate,
                         @RequestParam String sex, @RequestParam String email, @RequestParam String password) {
        try {
            this.userService.signIn(name, surname, birthdate, sex, email, password);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping(value = "/add-role")
    public String addRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole,
                          @RequestParam String idcategory) {
        try {
            this.userService.addRole(iduser, tokenuser, idrole, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping(value = "/remove-role")
    public String removeRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole) {
        try {
            this.userService.removeRole(iduser, tokenuser, idrole);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/my-roles")
    public RolesHandler getMyRoles(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return this.userService.getMyRoles(iduser, tokenuser);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/handy-roles-type")
    public List<String> getHandyRolesType() {
        try {
            return this.userService.getHandyRolesType();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/roles-type")
    public List<String> getRolesType() {
        try {
            return this.userService.getRolesType();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/my-roles-type")
    public List<String> getMyRolesType(@RequestParam Long iduser, @RequestParam Long tokenuser) {
        try {
            return this.userService.getMyRolesType(iduser, tokenuser);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/add-category-to-role")
    public String addCategoryToRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole,
                                    @RequestParam String idcategory) {
        try {
            this.userService.addCategory(iduser, tokenuser, idrole, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping(value = "/remove-category-to-role")
    public String removeCategoryToRole(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole,
                                       @RequestParam String idcategory) {
        try {
            this.userService.removeCategory(iduser, tokenuser, idrole, idcategory);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/list-history")
    public List<Project> listHistory(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole) {
        try {
            return List.copyOf(this.userService.listHistory(iduser, tokenuser, idrole));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-projects")
    public List<Project> listProjects(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole) {
        try {
            return List.copyOf(this.userService.listProjects(iduser, tokenuser, idrole));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-categories")
    public List<Category> listCategories(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole) {
        try {
            return List.copyOf(this.userService.listCategories(iduser, tokenuser, idrole));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/list-notifications")
    public List<Notification> listNotifications(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String idrole) {
        try {
            return this.userService.listNotifications(iduser, tokenuser, idrole);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/send-email")
    public String sendEmail(@RequestParam Long iduser, @RequestParam Long tokenuser, @RequestParam String message) {
        try {
            this.userService.sendEmail(iduser, tokenuser, message);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private class Credential {
        private Long id;
        private Long token;

        public Credential(User user) {
            this.id = user.getId();
            this.token = user.tokenHandlerGet().getToken();
        }

        public Credential() {
        }

        public Long getId() {
            return id;
        }

        public Long getToken() {
            return token;
        }
    }
}
