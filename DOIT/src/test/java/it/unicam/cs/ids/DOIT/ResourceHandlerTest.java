
package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.project.IProject;
import it.unicam.cs.ids.DOIT.role.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.service.*;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceHandlerTest {
    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IUser user4;
    private IProject project1;
    private IProject project2;
    private ICategory category1;
    private ICategory category2;

    @BeforeEach
    void init() {
        try {
            ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
            ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
            category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
            category2 = ServicesHandler.getInstance().getFactoryModel().createCategory("Informatica", "Descrizione.");
            user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
            user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Giacomo", "Simonetti", "1999", "Male");
            user3 = ServicesHandler.getInstance().getFactoryModel().createUser("Franca", "Suria", "1994", "Female");
            user4 = ServicesHandler.getInstance().getFactoryModel().createUser("Sara", "Giampitelli", "2000", "Female");
            user2.addRole(ProjectProposerRole.class, category1.getName());
            user4.addRole(ProjectProposerRole.class, category2.getName());
            user2.getRole(ProjectProposerRole.class).createProject("Campo da calcio", "calcio a 5", category1.getName());
            project1 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
            user4.getRole(ProjectProposerRole.class).createProject("SO", "sistema operativo", category2.getName());
            project2 = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "SO").findFirst().orElse(null);
            user1.addRole(ProjectManagerRole.class, category1.getName());
            user1.addRole(ProgramManagerRole.class, category2.getName());
            user2.addRole(ProjectManagerRole.class,category2.getName());
            user3.addRole(ProgramManagerRole.class, category1.getName());
            user3.addRole(DesignerRole.class,category2.getName());
            user4.addRole(DesignerRole.class,category1.getName());
            user4.addRole(ProgramManagerRole.class,category1.getName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testSearchOne(){
        assertEquals(category1, ServicesHandler.getInstance().getResourceHandler().getCategory(category1.getName()));
        assertEquals(category2, ServicesHandler.getInstance().getResourceHandler().getCategory(category2.getName()));
        assertEquals(user1, ServicesHandler.getInstance().getResourceHandler().getUser(user1.getId()));
        assertEquals(user2, ServicesHandler.getInstance().getResourceHandler().getUser(user2.getId()));
        assertEquals(user3, ServicesHandler.getInstance().getResourceHandler().getUser(user3.getId()));
        assertEquals(user4, ServicesHandler.getInstance().getResourceHandler().getUser(user4.getId()));
        assertEquals(project1, ServicesHandler.getInstance().getResourceHandler().getProject(project1.getId()));
        assertEquals(project2, ServicesHandler.getInstance().getResourceHandler().getProject(project2.getId()));
    }

    @Test
    void testSearch(){
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllCategories().contains(category1));
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllCategories().contains(category2));
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllUsers().contains(user1));
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllUsers().contains(user2));
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllUsers().contains(user3));
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllUsers().contains(user4));
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllProjects().contains(project1));
        assertTrue(ServicesHandler.getInstance().getResourceHandler().getAllProjects().contains(project1));
    }


}
