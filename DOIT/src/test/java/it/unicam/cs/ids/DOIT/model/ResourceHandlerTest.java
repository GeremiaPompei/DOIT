package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceHandlerTest {
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private Project project1;
    private Project project2;
    private Category category1;
    private Category category2;
    private IResourceHandler resourceHandler;
    private UtilityFactory factory;

    @BeforeEach
    void init() {
        try {
            resourceHandler = new ResourceHandler();
            factory = new UtilityFactory(resourceHandler);
            category1 = factory.createCategory("Sport", "Descrizione.");
            category2 = factory.createCategory("Informatica", "Descrizione.");
            user1 = factory.createUser(1, "Daniele", "Baiocco", 1930, "Male");
            user2 = factory.createUser(2, "Giacomo", "Simonetti", 1999, "Male");
            user3 = factory.createUser(3, "Franca", "Suria", 1994, "Female");
            user4 = factory.createUser(4, "Sara", "Giampitelli", 2000, "Female");
            user2.addRole(ProjectProposerRole.class, category1);
            user4.addRole(ProjectProposerRole.class, category2);
            project1 = user2.getRole(ProjectProposerRole.class).createProject(1, "Campo da calcio", "calcio a 5", category1, factory);
            project2 = user4.getRole(ProjectProposerRole.class).createProject(2, "SO", "sistema operativo", category2, factory);
            user1.addRole(ProjectManagerRole.class, category1);
            user1.addRole(ProgramManagerRole.class, category2);
            user2.addRole(ProjectManagerRole.class,category2);
            user3.addRole(ProgramManagerRole.class, category1);
            user3.addRole(DesignerRole.class,category2);
            user4.addRole(DesignerRole.class,category1);
            user4.addRole(ProgramManagerRole.class,category1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testSearchOne(){
        assertEquals(category1, resourceHandler.searchOne(Category.class, x -> x.getName().equals(category1.getName())));
        assertEquals(category2, resourceHandler.searchOne(Category.class, x -> x.getName().equals(category2.getName())));
        assertEquals(user1, resourceHandler.searchOne(User.class, x->x.getId()==1));
        assertEquals(user2, resourceHandler.searchOne(User.class, x->x.getId()==2));
        assertEquals(user3, resourceHandler.searchOne(User.class, x->x.getId()==3));
        assertEquals(user4, resourceHandler.searchOne(User.class, x->x.getId()==4));
        assertEquals(project1, resourceHandler.searchOne(Project.class, x->x.getId()==1));
        assertEquals(project2, resourceHandler.searchOne(Project.class, x->x.getId()==2));
        assertEquals(project1, resourceHandler.searchOne(Project.class, x->x.getCategory().equals(category1)));
        assertEquals(user3, resourceHandler.searchOne(User.class, x-> {
            try {
                return x.getRole(DesignerRole.class).getCategories().contains(category2);
            } catch (RoleException e) {
                return false;
            }
        }));
        assertEquals(user1, resourceHandler.searchOne(User.class, x-> {
            try {
                return x.getRole(ProjectManagerRole.class).getCategories().contains(category1);
            } catch (RoleException e) {
                return false;
            }
        }));
    }

    @Test
    void testSearch(){
        assertTrue(resourceHandler.search(Category.class, x -> true).contains(category1));
        assertTrue(resourceHandler.search(Category.class, x -> true).contains(category2));
        assertTrue(resourceHandler.search(User.class, x -> true).contains(user1));
        assertTrue(resourceHandler.search(User.class, x -> true).contains(user2));
        assertTrue(resourceHandler.search(User.class, x -> true).contains(user3));
        assertTrue(resourceHandler.search(User.class, x -> true).contains(user4));
        assertTrue(resourceHandler.search(Project.class, x -> true).contains(project1));
        assertTrue(resourceHandler.search(Project.class, x -> true).contains(project2));
        assertTrue(resourceHandler.search(User.class, x -> {
            try {
                 x.getRole(DesignerRole.class);
                 return true;
            } catch (RoleException e) {
               return false;
            }
        }).contains(user3));
        assertTrue(resourceHandler.search(User.class, x -> {
            try {
                x.getRole(DesignerRole.class);
                return true;
            } catch (RoleException e) {
                return false;
            }
        }).contains(user4));
    }


}