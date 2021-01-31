package it.unicam.cs.ids.DOIT.repository;

import it.unicam.cs.ids.DOIT.model.role.DesignerRole;
import it.unicam.cs.ids.DOIT.model.role.ProgramManagerRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryHandler {

    private static RepositoryHandler repositoryHandler;

    public static RepositoryHandler getInstance() {
        if (repositoryHandler == null)
            repositoryHandler = new RepositoryHandler();
        return repositoryHandler;
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PartecipationRequestRepository<DesignerRole> partecipationRequestDesignerRepository;
    @Autowired
    private PartecipationRequestRepository<ProgramManagerRole> partecipationRequestProgramManagerRepository;
    @Autowired
    private ProjectManagerRepository projectManagerRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public PartecipationRequestRepository<DesignerRole> getPartecipationRequestDesignerRepository() {
        return partecipationRequestDesignerRepository;
    }

    public PartecipationRequestRepository<ProgramManagerRole> getPartecipationRequestProgramManagerRepository() {
        return partecipationRequestProgramManagerRepository;
    }

    public ProjectStateRepository getProjectStateRepository() {
        return projectStateRepository;
    }

    public ProjectManagerRepository getProjectManagerRepository() {
        return projectManagerRepository;
    }

    public EvaluationRepository getEvaluationRepository() {
        return evaluationRepository;
    }

    public NotificationRepository getNotificationRepository() {
        return notificationRepository;
    }
}
