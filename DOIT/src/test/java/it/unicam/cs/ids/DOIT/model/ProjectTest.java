package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.IProjectProposerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.storage.FactoryStorage;
import it.unicam.cs.ids.DOIT.storage.IResourceHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    private IUser user1;
    private IFactoryModel factory;
    private IResourceHandler resourceHandler;

    @Test
    void testCreateProject(){
        try{
            resourceHandler = FactoryStorage.getResouceHandler();
            resourceHandler.search(Object.class, t->true).forEach(t->resourceHandler.remove(t));
            factory = new FactoryModel();
            factory.createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            factory.createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            factory.createProjectState(2, "TERMINALE", "Stato terminale.");
            user1 = factory.createUser(1,"Daniele","Baiocco", 1930, "Male");
            ICategory category = factory.createCategory("SPORT","descrizione");
            user1.addRole(ProjectProposerRole.class, category, factory);
            IProject project = user1.getRole(IProjectProposerRole.class).createProject(1,"campo calcio","calcio a 5", category, factory);
            assertEquals(project, resourceHandler.searchOne(IProject.class, x -> x.getId() == 1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}