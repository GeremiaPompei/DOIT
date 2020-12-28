package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.ProjectProposerRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    private User user1;
    @Test
    void testCreateProject(){
        try{
            GestoreRisorse.getInstance().clear();
            user1 = new User(1,"Daniele","Baiocco", 1930, "Male");
            Category category = new Category("SPORT","descrizione");
            user1.addRole(ProjectProposerRole.class, category);
            Project project =user1.getRole(ProjectProposerRole.class).createProject(1,"campo calcio","calcio a 5", category);
            assertEquals(project, GestoreRisorse.getInstance().searchOne(Project.class, x -> x.getId() == 1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}