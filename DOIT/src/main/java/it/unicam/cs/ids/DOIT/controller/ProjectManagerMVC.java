package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectManagerMVC {
    @Autowired
    private UserHandler userHandler;
}
