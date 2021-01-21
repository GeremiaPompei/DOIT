package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.IUserHandler;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;

@RestController
@RequestMapping("/api/user")
public class UserController {
    IUserHandler userHandler;

    @Autowired
    public UserController(@Qualifier("userHandler") IUserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @PostMapping(value = "/login")
    public String logIn(@RequestBody String args) {
        String[] params = args.split(" ");
        IUser user = this.userHandler.logIn(params[0], params[1]);
        return user.getId() + " " + user.getToken().getToken();
    }

    @PostMapping(value = "/logout")
    public String logOut(@RequestBody String args) {
        String[] params = args.split(" ");
        this.userHandler.logOut(Long.parseLong(params[0]),Long.parseLong(params[1]));
        return "success";
    }

    @PostMapping(value = "/signin")
    public String signIn(@RequestBody String args) {
        String[] params = args.split(" ");
        this.userHandler.signIn(params[0], params[1], params[2], params[3], params[4], params[5]);
        return "success";
    }
}