package it.unicam.cs.ids.DOIT.view.controllerspringboot;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {
    @GetMapping(value = "/")
    public RedirectView redirectToIndex() {
        return new RedirectView("/index.html");
    }

}
