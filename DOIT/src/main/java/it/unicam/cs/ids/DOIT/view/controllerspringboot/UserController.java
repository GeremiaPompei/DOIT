package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.IUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    IUserHandler userHandler;

    @Autowired
    public UserController(@Qualifier("userHandler") IUserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @PostMapping(value = "/login")
    public String logIn() {
        return "ok";
    }

    @PostMapping(value = "/logout")
    public String logOut() {
        return "ok";
    }

    @PostMapping(value = "/signin")
    public String signIn() {
        return "ok";
    }
}
