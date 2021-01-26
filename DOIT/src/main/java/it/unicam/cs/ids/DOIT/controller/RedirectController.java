package it.unicam.cs.ids.DOIT.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class RedirectController {
    @GetMapping(value = "/")
    public RedirectView redirectToIndex() {
        return new RedirectView("/index.html");
    }

}
