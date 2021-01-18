package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.view.ControllerView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.function.Function;

@RestController
public class SpringBootController {
    @GetMapping(value = "/")
    public RedirectView redirectToIndex() {
        return new RedirectView("/index.html");
    }

    @GetMapping(value = "/api/")
    public String get(@RequestParam String args) {
        Map<String, Map<String, Function<String[], String>>> commands;
        commands = new ControllerView(UserHandler.getInstance()).getCommands();
        System.out.println("***DOIT***");
        String[] params = args.split(" ");
        String[] params2 = new String[params.length - 1];
        for (int i = 0; i < params2.length; i++)
            params2[i] = params[i + 1];
        String result = commands.get(params[0]).get(params[1]).apply(params2);
        System.out.println("\n" + result + "\n");
        return result;
    }
}
