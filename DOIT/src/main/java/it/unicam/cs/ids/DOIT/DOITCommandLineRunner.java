package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.partecipation_request.PartecipationRequest;
import it.unicam.cs.ids.DOIT.project.ProjectState;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.service.entity.CategoryRepository;
import it.unicam.cs.ids.DOIT.service.entity.PartecipationRequestRepository;
import it.unicam.cs.ids.DOIT.service.entity.ProjectStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DOITCommandLineRunner implements CommandLineRunner {
    @Autowired
    private PartecipationRequestRepository<DesignerRole> prr;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProjectStateRepository projectStateRepository;
    @Override
    public void run(String... args) throws Exception {
        projectStateRepository.save(new ProjectState(0L,"INITIAL", "Description..."));
        projectStateRepository.save(new ProjectState(1L,"IN PROGRESS", "Description..."));
        projectStateRepository.save(new ProjectState(2L,"TERMINAL", "Description..."));
        categoryRepository.save(new Category("SPORT", "Description..."));
        categoryRepository.save(new Category("INFORMATICA", "Description..."));
        categoryRepository.save(new Category("CUCINA", "Description..."));
        //prr.save(new PartecipationRequest<>(new DesignerRole(null, null, null),null));
    }
}
