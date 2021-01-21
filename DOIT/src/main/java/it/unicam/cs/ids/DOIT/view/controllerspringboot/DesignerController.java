package it.unicam.cs.ids.DOIT.view.controllerspringboot;

import it.unicam.cs.ids.DOIT.controller.IUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/designer")
public class DesignerController {
    IUserHandler userHandler;

    @Autowired
    public DesignerController(@Qualifier("userHandler") IUserHandler userHandler) {
        this.userHandler = userHandler;
    }
}
