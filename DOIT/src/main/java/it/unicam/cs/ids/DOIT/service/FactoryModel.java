package it.unicam.cs.ids.DOIT.service;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.*;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.entity.CategoryRepository;
import it.unicam.cs.ids.DOIT.service.entity.ProjectRepository;
import it.unicam.cs.ids.DOIT.service.entity.ProjectStateRepository;
import it.unicam.cs.ids.DOIT.service.entity.UserRepository;
import it.unicam.cs.ids.DOIT.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class FactoryModel implements IFactoryModel {
    private static FactoryModel factoryModel;

    public static FactoryModel getInstance() {
        if (factoryModel == null)
            factoryModel = new FactoryModel();
        return factoryModel;
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;

    public Project createProject(String name, String description, Category category) {
        Project project = new Project(IdGenerator.getId(), name, description, category);
        projectRepository.save(project);
        return project;
    }

    public Category createCategory(String name, String description) {
        Category category = new Category(name, description);
        categoryRepository.save(category);
        return category;
    }

    public ProjectState createProjectState(Long id, String name, String description) {
        ProjectState projectState = new ProjectState(id, name, description);
        projectStateRepository.save(projectState);
        return projectState;
    }

    public User createUser(String name, String surname, String birthdDay, String sex, String email, String password) {
        User user = new User(IdGenerator.getId(), name, surname, birthdDay, sex, email, password);
        userRepository.save(user);
        return user;
    }

    @Override
    public PartecipationRequest createPartecipationRequest(PendingRole role, Team team) {
        PartecipationRequest partecipationRequest = new PartecipationRequest(role, team);
        return partecipationRequest;
    }

    @Override
    public Team createTeam(Project project, ProjectProposerRole projectProposer) {
        return new Team(project, projectProposer);
    }

    @Override
    public <T extends Role> T createRole(Class<T> clazz, User user, Category category) throws ReflectiveOperationException {
        return clazz.getConstructor(new Class<?>[]{User.class, Category.class}).newInstance(new Object[]{user, category});
    }
}
