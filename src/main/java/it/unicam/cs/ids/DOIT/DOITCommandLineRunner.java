package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.user.User;
import it.unicam.cs.ids.DOIT.user.UserService;
import it.unicam.cs.ids.DOIT.entity.project.ProjectState;
import it.unicam.cs.ids.DOIT.repository.CategoryRepository;
import it.unicam.cs.ids.DOIT.repository.ProjectStateRepository;
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
    private UserService userMVC;

    @Override
    public void run(String... args) throws Exception {
        projectStateRepository.save(new ProjectState(0L, "INITIAL",
                "Initial state."));
        projectStateRepository.save(new ProjectState(1L, "IN PROGRESS",
                "Evolution state."));
        projectStateRepository.save(new ProjectState(2L, "TERMINAL",
                "Terminal state."));
        categoryRepository.save(new Category("WORKSHOP",
                "Nel corso delle Giornate CUIA in Argentina e in Italia. Bando CUIA e relative collaborazioni con le reti UNIR e RUNDO."));
        categoryRepository.save(new Category("PROGETTI DI RICERCA",
                "In risposta ad altri bandi."));
        categoryRepository.save(new Category("PROGETTI DI COOPERAZIONE BILATERALE",
                "Italia Argentina in risposta a bandi."));
        categoryRepository.save(new Category("ATTIVITA' FORMATIVA",
                "Corsi di formazione, specializzazione, scuole estive o invernali."));
        categoryRepository.save(new Category("DOUBLE DEGREE",
                "Dottorati in co-tutela."));
        categoryRepository.save(new Category("PUBBLICAZIONI CONGIUNTE",
                ""));
    }
}
