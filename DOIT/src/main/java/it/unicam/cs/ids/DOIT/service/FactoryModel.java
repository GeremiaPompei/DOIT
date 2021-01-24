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

@Service
public class FactoryModel implements IFactoryModel {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectStateRepository projectStateRepository;

    public Project createProject(String name, String description, Category category, ProjectState projectState) {
        Project project = new Project(name, description, category, projectState);
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
        User user = new User(name, surname, birthdDay, sex, email, password, this);
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
    public ProjectProposerRole createProjectProposerRole(User user, Category category) {
        return new ProjectProposerRole(user, category, this);
    }

    @Override
    public ProgramManagerRole createProgramManagerRole(User user, Category category) {
        return new ProgramManagerRole(user, category, this);
    }

    @Override
    public DesignerRole createDesignerRole(User user, Category category) {
        return new DesignerRole(user, category, this);
    }

    @Override
    public ProjectManagerRole createProjectManagerRole(User user, Category category) {
        return new ProjectManagerRole(user, category, this);
    }
}
