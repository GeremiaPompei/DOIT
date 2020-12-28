package it.unicam.cs.ids.DOIT.model.roles;

import it.unicam.cs.ids.DOIT.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProjectProposerRoleTest {
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private Category category1;
    private Category category2;

    @BeforeEach
    void init() throws ReflectiveOperationException{
        GestoreRisorse.getInstance().clear();
        new Category("Sport", "Descrizione.");
        new Category("Informatica", "Descrizione.");
        new Category("Domotica", "Descrizione.");
        user1 = new User(1,"Daniele","Baiocco", 1930, "Male");
        user2 = new User(2, "Giacomo", "Simonetti",1999, "Male");
        user3 = new User(3, "Franca", "Suria",1994, "Female");
        user4 = new User(4, "Sara", "Giampitelli",2000, "Female");
        category1= GestoreRisorse.getInstance().searchOne(Category.class, x->x.getName().equals("SPORT"));
        category2 = GestoreRisorse.getInstance().searchOne(Category.class, x->x.getName().equals("INFORMATICA"));
        user2.addRole(ProjectProposerRole.class,category1);
        user4.addRole(ProjectProposerRole.class, category2);
        user1.addRole(ProgramManagerRole.class, category1);
        user3.addRole(ProgramManagerRole.class, category2);
    }

    @Test
    void testCreateProject(){
        try {
            assertThrows(IllegalArgumentException.class,
            ()-> {
                user2.getRole(ProjectProposerRole.class).createProject(1, "SO", "sistema operativo", category2);
            }, "L'utente non presenta la categoria: [INFORMATICA]");
           Project project = user2.getRole(ProjectProposerRole.class).createProject(1,"Campo da calcio","calcio a 5",category1);
            assertTrue(user2.getRole(ProjectProposerRole.class).getProjects().contains(project));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testFindProgramManagerList(){
        try {
            Set<User> userSet= user2.getRole(ProjectProposerRole.class).findProgramManagerList(category1);
            assertTrue(userSet.contains(user1));
            assertFalse(userSet.contains(user3));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testCreateTeam(){
        try {
            Project project = user2.getRole(ProjectProposerRole.class).createProject(1,"Campo da calcio","calcio a 5",category1);
        assertThrows(RoleException.class,
                ()-> {
                    user2.getRole(ProjectProposerRole.class).createTeam(user2,project);
        });
        assertThrows(IllegalArgumentException.class,
                    ()-> {
                        user2.getRole(ProjectProposerRole.class).createTeam(user3,project);
                    });
        assertThrows(RoleException.class,
                ()-> {
                    user2.getRole(ProjectProposerRole.class).createTeam(user4,project);
                });
        
        user2.getRole(ProjectProposerRole.class).createTeam(user1, project);
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
}