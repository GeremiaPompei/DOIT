package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.controller.ProjectProposerMVC;
import it.unicam.cs.ids.DOIT.controller.UserMVC;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.category.CategoryRepository;
import it.unicam.cs.ids.DOIT.project.ProjectStateRepository;
import it.unicam.cs.ids.DOIT.user.User;
import it.unicam.cs.ids.DOIT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DOITCommandLineRunner implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;
    @Autowired
    private UserMVC userMVC;

    @Override
    public void run(String... args) throws Exception {
        projectStateRepository.save(new ProjectState(0L, "INITIAL", "Description..."));
        projectStateRepository.save(new ProjectState(1L, "IN PROGRESS", "Description..."));
        projectStateRepository.save(new ProjectState(2L, "TERMINAL", "Description..."));
        categoryRepository.save(new Category("SPORT", "Description..."));
        categoryRepository.save(new Category("INFORMATICA", "Description..."));
        categoryRepository.save(new Category("CUCINA", "Description..."));


        userMVC.signIn("Nome", "Cognome", "Eta", "Sesso", "Email", "Password");
        User user = userMVC.logIn("Email", "Password");
        userMVC.addRole(user.getId(), user.getToken().getToken(), "project-proposer", "SPORT");
        userMVC.addRole(user.getId(), user.getToken().getToken(), "program-manager", "SPORT");
        userMVC.addRole(user.getId(), user.getToken().getToken(), "designer", "SPORT");
        System.err.println(user.getId() + " " + user.getToken().getToken());
    }
}
