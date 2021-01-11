package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    private User user1;
    private UtilityFactory factory;
    private IResourceHandler resourceHandler;

    @Test
    void testCreateProject(){
        try{
            resourceHandler = new ResourceHandler();
            factory = new UtilityFactory(resourceHandler);
            user1 = factory.createUser(1,"Daniele","Baiocco", 1930, "Male");
            Category category = factory.createCategory("SPORT","descrizione");
            user1.addRole(ProjectProposerRole.class, category);
            Project project =user1.getRole(ProjectProposerRole.class).createProject(1,"campo calcio","calcio a 5", category, factory);
            assertEquals(project, resourceHandler.searchOne(Project.class, x -> x.getId() == 1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}