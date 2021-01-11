package it.unicam.cs.ids.DOIT.model;

import it.unicam.cs.ids.DOIT.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.model.roles.initial.ProjectProposerRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    private User user1;
    private User user2;
    private User user3;
    private Project project1;
    private Category category1;
    private PartecipationRequest partecipationRequest;
    private IResourceHandler resourceHandler;
    private UtilityFactory factory;

    @BeforeEach
    void init() {
        try {
            resourceHandler = new ResourceHandler();
            factory = new UtilityFactory(resourceHandler);
            category1 = factory.createCategory("Sport", "Descrizione.");
            user1 = factory.createUser(1, "Daniele", "Baiocco", 1930, "Male");
            user2 = factory.createUser(2, "Giacomo", "Simonetti", 1999, "Male");
            user3 = factory.createUser(3, "Sio", "Sandrio", 2000, "Male");
            user1.addRole(ProjectProposerRole.class, category1);
            project1 =user1.getRole(ProjectProposerRole.class).createProject(1, "Progetto1","Campo da Beach",category1, factory);
            user2.addRole(ProgramManagerRole.class, category1);
            user2.getRole(ProgramManagerRole.class).createTeam(project1);
            user3.addRole(DesignerRole.class, category1);
            partecipationRequest = user3.getRole(DesignerRole.class).createPartecipationRequest(project1.getTeam());
        } catch (ReflectiveOperationException | RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddDesigner(){
        try {
            user2.getRole(ProgramManagerRole.class).addDesigner(partecipationRequest);
            assertFalse(project1.getTeam().getPartecipationRequests().contains(partecipationRequest));
            assertTrue(project1.getTeam().getDesigners().contains(user3));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testRemoveDesigner() throws RoleException {
        Team team = user2.getRole(ProgramManagerRole.class).getTeams().stream().findFirst().get();
        team.removeDesigner(user3);
        assertFalse(project1.getTeam().getDesigners().contains(user3));
    }

}