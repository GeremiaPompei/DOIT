package it.unicam.cs.ids.DOIT.view;

import it.unicam.cs.ids.DOIT.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SpringBootController {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private ControllerView controllerView;

    @GetMapping(value = "/")
    public RedirectView redirectToIndex() {
        return new RedirectView("/index.html");
    }

    @GetMapping(value = "/api/")
    public String get(@RequestParam String args) {
        String[] params = args.split(" ");
        String cmd = params[0];
        for (int i = 0; i < params.length - 1; i++)
            params[i] = params[i + 1];
        String result = controllerView.getCommands().get(cmd).get(params[0]).apply(params);
        System.out.println(result);
        return result;
    }

    @PostMapping
    public String logIn(@RequestBody LogIn logIn) {
        System.err.println(logIn.email + " " + logIn.password);
        return "ok";
    }

    public class LogIn {
        private String email;
        private String password;

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public LogIn() {
        }
    }
}
