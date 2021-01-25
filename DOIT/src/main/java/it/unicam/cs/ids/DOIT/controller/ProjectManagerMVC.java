package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectManagerMVC {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;

    public Set<Project> listProjects(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getProjects();
    }

    public Set<Category> listCategories(Long idUser, Long tokenUser) {
        User user = userRepository.findById(idUser).get();
        return userHandler.getUser(user, tokenUser).getRolesHandler().getProjectManagerRole().getCategories();
    }
}
