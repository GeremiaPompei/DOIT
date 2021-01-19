package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.view.ControllerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
        for (int i = 0; i < params.length-1; i++)
            params[i] = params[i + 1];
        String result = controllerView.getCommands().get(cmd).get(params[0]).apply(params);
        System.out.println(result);
        return result;
    }
}
