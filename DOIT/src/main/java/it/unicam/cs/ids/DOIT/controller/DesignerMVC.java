package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectRepository;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignerMVC {

    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public void sendPR(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);
        userHandler.getUser(user, tokenUser).getRolesHandler().getDesignerRole().createPartecipationRequest(project);
        userRepository.save(user);
    }
}
