package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.category.CategoryRepository;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.project.ProjectStateRepository;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectProposerMVC {

    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;

    public void createProject(Long idUser, Long tokenUser, String name, String description, String idCategory) {
        User user = userRepository.findById(idUser).get();
        Category category = categoryRepository.findById(idCategory).get();
        ProjectState projectState = projectStateRepository.findById(0L).get();
        userHandler.getUser(user, tokenUser).getRolesHandler().getProjectProposerRole()
                .createProject(name,description,category,projectState);
        userRepository.save(user);
    }

}
