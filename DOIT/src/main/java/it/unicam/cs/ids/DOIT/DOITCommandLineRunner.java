package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.category.Category;
import it.unicam.cs.ids.DOIT.model.user.User;
import it.unicam.cs.ids.DOIT.service.UserService;
import it.unicam.cs.ids.DOIT.model.project.ProjectState;
import it.unicam.cs.ids.DOIT.repository.CategoryRepository;
import it.unicam.cs.ids.DOIT.repository.ProjectStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DOITCommandLineRunner implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;
    @Autowired
    private UserService userMVC;

    @Override
    public void run(String... args) throws Exception {
        projectStateRepository.save(new ProjectState(0L, "INITIAL", "Description..."));
        projectStateRepository.save(new ProjectState(1L, "IN PROGRESS", "Description..."));
        projectStateRepository.save(new ProjectState(2L, "TERMINAL", "Description..."));
        categoryRepository.save(new Category("SPORT", "Description..."));
        categoryRepository.save(new Category("INFORMATICA", "Description..."));
        categoryRepository.save(new Category("CUCINA", "Description..."));

        //TODO da eliminare
        userMVC.signIn("nome", "cognome", "1999-09-09", "sesso", "a",
                "0cc175b9c0f1b6a831c399e269772661");
        User user = userMVC.logIn("a", "0cc175b9c0f1b6a831c399e269772661");
        userMVC.addRole(user.getId(), user.tokenHandlerGet().getToken(), "project-proposer", "SPORT");
        userMVC.addRole(user.getId(), user.tokenHandlerGet().getToken(), "program-manager", "SPORT");
        userMVC.addRole(user.getId(), user.tokenHandlerGet().getToken(), "designer", "SPORT");
        System.err.println(user.getId() + " " + user.tokenHandlerGet().getToken());
    }
}
