/*

package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.model.category.ICategory;
import it.unicam.cs.ids.DOIT.model.project.IProject;
import it.unicam.cs.ids.DOIT.model.role.*;
import it.unicam.cs.ids.DOIT.service.*;
import it.unicam.cs.ids.DOIT.model.user.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProjectProposerRoleTest {
    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IUser user4;
    private ICategory category1;
    private ICategory category2;
    private ICategory category3;

    @BeforeEach
    void init() throws ReflectiveOperationException {
        ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
        ServicesHandler.getInstance().getFactoryModel().createProjectState(0, "INIZIALIZZAZIONE", "Stato iniziale.");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(1, "SVILUPPO", "Stato di sviluppo.");
        ServicesHandler.getInstance().getFactoryModel().createProjectState(2, "TERMINALE", "Stato terminale.");
        category1 = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
        category2 = ServicesHandler.getInstance().getFactoryModel().createCategory("Informatica", "Descrizione.");
        category3 = ServicesHandler.getInstance().getFactoryModel().createCategory("Domotica", "Descrizione.");
        user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Daniele", "Baiocco", "1930", "Male");
        user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Giacomo", "Simonetti", "1999", "Male");
        user3 = ServicesHandler.getInstance().getFactoryModel().createUser("Franca", "Suria", "1994", "Female");
        user4 = ServicesHandler.getInstance().getFactoryModel().createUser("Sara", "Giampitelli", "2000", "Female");
        user2.addRole(ProjectProposerRole.class, category1.getName());
        user4.addRole(ProjectProposerRole.class, category2.getName());
        user1.addRole(ProgramManagerRole.class, category1.getName());
        user3.addRole(ProgramManagerRole.class, category2.getName());
    }

    @Test
    void testCreateProject() {
        try {
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProjectProposerRole.class).createProject("SO", "sistema operativo", category2.getName());
                    }, "L'utente non presenta la categoria: [INFORMATICA]");
            user2.getRole(ProjectProposerRole.class).createProject("Campo da calcio", "calcio a 5", category1.getName());
            IProject project = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
            user2.getRole(ProjectProposerRole.class).createTeam(user1.getId(), project.getId());
            assertTrue(user2.getRole(ProjectProposerRole.class).getTeams().contains(project.getTeam()));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testFindProgramManagerList() {
        try {
            Set<IUser> userSet = user2.getRole(ProjectProposerRole.class).findProgramManagerList(category1.getName());
            assertTrue(userSet.contains(user1));
            assertFalse(userSet.contains(user3));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testCreateTeam() {
        try {
            user2.getRole(ProjectProposerRole.class).createProject("Campo da calcio", "calcio a 5", category1.getName());
            IProject project = ServicesHandler.getInstance().getResourceHandler().getAllProjects()
                    .stream().filter(x -> x.getName() == "Campo da calcio").findFirst().orElse(null);
            assertThrows(RoleException.class,
                    () -> {
                        user2.getRole(ProjectProposerRole.class).createTeam(user2.getId(), project.getId());
                    });
            assertThrows(IllegalArgumentException.class,
                    () -> {
                        user2.getRole(ProjectProposerRole.class).createTeam(user3.getId(), project.getId());
                    });
            assertThrows(RoleException.class,
                    () -> {
                        user2.getRole(ProjectProposerRole.class).createTeam(user4.getId(), project.getId());
                    });

            user2.getRole(ProjectProposerRole.class).createTeam(user1.getId(), project.getId());
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
}
*/
