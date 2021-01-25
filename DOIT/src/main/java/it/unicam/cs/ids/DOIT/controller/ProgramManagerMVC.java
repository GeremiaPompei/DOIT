package it.unicam.cs.ids.DOIT.controller;

import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequestRepository;
import it.unicam.cs.ids.DOIT.project.Project;
import it.unicam.cs.ids.DOIT.project.ProjectRepository;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserHandler;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramManagerMVC {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PartecipationRequestRepository<ProgramManagerRole> partecipationRequestRepository;

    public void sendPR(Long idUser, Long tokenUser, Long projectId) {
        User user = userRepository.findById(idUser).get();
        Project project = projectRepository.findById(projectId).get();
        PartecipationRequest<ProgramManagerRole> pr =
                userHandler.getUser(user, tokenUser).getRolesHandler().getProgramManagerRole().createPartecipationRequest(project);
        partecipationRequestRepository.save(pr);
        userRepository.save(user);
    }
}
