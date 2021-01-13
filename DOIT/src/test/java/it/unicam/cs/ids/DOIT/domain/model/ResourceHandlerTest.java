package it.unicam.cs.ids.DOIT.domain.model;

import it.unicam.cs.ids.DOIT.domain.model.roles.*;
import it.unicam.cs.ids.DOIT.simple.model.roles.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.DesignerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.simple.model.FactoryModel;
import it.unicam.cs.ids.DOIT.simple.storage.ResourceHandler;
import it.unicam.cs.ids.DOIT.domain.storage.IResourceHandler;
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
    private IResourceHandler resourceHandler;
    private IFactoryModel factory;

    @BeforeEach
    void init() {
        try {
            resourceHandler = new ResourceHandler();
            resourceHandler.search(Object.class, t->true).forEach(t->resourceHandler.remove(t));
            factory = new FactoryModel(resourceHandler);
            factory.createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
            factory.createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
            factory.createProjectState(2, "TERMINALE", "Stato terminale.");
            category1 = factory.createCategory("Sport", "Descrizione.");
            category2 = factory.createCategory("Informatica", "Descrizione.");
            user1 = factory.createUser(1, "Daniele", "Baiocco", 1930, "Male");
            user2 = factory.createUser(2, "Giacomo", "Simonetti", 1999, "Male");
            user3 = factory.createUser(3, "Franca", "Suria", 1994, "Female");
            user4 = factory.createUser(4, "Sara", "Giampitelli", 2000, "Female");
            user2.addRole(ProjectProposerRole.class, category1, factory);
            user4.addRole(ProjectProposerRole.class, category2, factory);
            project1 = user2.getRole(IProjectProposerRole.class).createProject(1, "Campo da calcio", "calcio a 5", category1, factory);
            project2 = user4.getRole(IProjectProposerRole.class).createProject(2, "SO", "sistema operativo", category2, factory);
            user1.addRole(ProjectManagerRole.class, category1, factory);
            user1.addRole(ProgramManagerRole.class, category2, factory);
            user2.addRole(ProjectManagerRole.class,category2, factory);
            user3.addRole(ProgramManagerRole.class, category1, factory);
            user3.addRole(DesignerRole.class,category2, factory);
            user4.addRole(DesignerRole.class,category1, factory);
            user4.addRole(ProgramManagerRole.class,category1, factory);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testSearchOne(){
        assertEquals(category1, resourceHandler.searchOne(ICategory.class, x -> x.getName().equals(category1.getName())));
        assertEquals(category2, resourceHandler.searchOne(ICategory.class, x -> x.getName().equals(category2.getName())));
        assertEquals(user1, resourceHandler.searchOne(IUser.class, x->x.getId()==1));
        assertEquals(user2, resourceHandler.searchOne(IUser.class, x->x.getId()==2));
        assertEquals(user3, resourceHandler.searchOne(IUser.class, x->x.getId()==3));
        assertEquals(user4, resourceHandler.searchOne(IUser.class, x->x.getId()==4));
        assertEquals(project1, resourceHandler.searchOne(IProject.class, x->x.getId()==1));
        assertEquals(project2, resourceHandler.searchOne(IProject.class, x->x.getId()==2));
        assertEquals(project1, resourceHandler.searchOne(IProject.class, x->x.getCategory().equals(category1)));
        assertEquals(user3, resourceHandler.searchOne(IUser.class, x-> {
            try {
                return x.getRole(IDesignerRole.class).getCategories().contains(category2);
            } catch (RoleException e) {
                return false;
            }
        }));
        assertEquals(user1, resourceHandler.searchOne(IUser.class, x-> {
            try {
                return x.getRole(IProjectManagerRole.class).getCategories().contains(category1);
            } catch (RoleException e) {
                return false;
            }
        }));
    }

    @Test
    void testSearch(){
        assertTrue(resourceHandler.search(ICategory.class, x -> true).contains(category1));
        assertTrue(resourceHandler.search(ICategory.class, x -> true).contains(category2));
        assertTrue(resourceHandler.search(IUser.class, x -> true).contains(user1));
        assertTrue(resourceHandler.search(IUser.class, x -> true).contains(user2));
        assertTrue(resourceHandler.search(IUser.class, x -> true).contains(user3));
        assertTrue(resourceHandler.search(IUser.class, x -> true).contains(user4));
        assertTrue(resourceHandler.search(IProject.class, x -> true).contains(project1));
        assertTrue(resourceHandler.search(IProject.class, x -> true).contains(project2));
        assertTrue(resourceHandler.search(IUser.class, x -> {
            try {
                 x.getRole(IDesignerRole.class);
                 return true;
            } catch (RoleException e) {
               return false;
            }
        }).contains(user3));
        assertTrue(resourceHandler.search(IUser.class, x -> {
            try {
                x.getRole(IDesignerRole.class);
                return true;
            } catch (RoleException e) {
                return false;
            }
        }).contains(user4));
    }


}