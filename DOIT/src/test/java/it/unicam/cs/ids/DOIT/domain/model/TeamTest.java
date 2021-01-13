package it.unicam.cs.ids.DOIT.domain.model;

import it.unicam.cs.ids.DOIT.domain.model.roles.IDesignerRole;
import it.unicam.cs.ids.DOIT.domain.model.roles.IProgramManagerRole;
import it.unicam.cs.ids.DOIT.domain.model.roles.IProjectProposerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.initial.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.initial.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.simple.model.FactoryModel;
import it.unicam.cs.ids.DOIT.simple.storage.ResourceHandler;
import it.unicam.cs.ids.DOIT.domain.storage.IResourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    private IUser user1;
    private IUser user2;
    private IUser user3;
    private IProject project1;
    private ICategory category1;
    private IPartecipationRequest partecipationRequest;
    private IResourceHandler resourceHandler;
    private IFactoryModel factory;

    @BeforeEach
    void init() {
        try {
            resourceHandler = new ResourceHandler();
            resourceHandler.search(Object.class, t->true).forEach(t->resourceHandler.remove(t));
            factory = new FactoryModel(resourceHandler);
            category1 = factory.createCategory("Sport", "Descrizione.");
            user1 = factory.createUser(1, "Daniele", "Baiocco", 1930, "Male");
            user2 = factory.createUser(2, "Giacomo", "Simonetti", 1999, "Male");
            user3 = factory.createUser(3, "Sio", "Sandrio", 2000, "Male");
            user1.addRole(ProjectProposerRole.class, category1, factory);
            project1 =user1.getRole(IProjectProposerRole.class).createProject(1, "Progetto1","Campo da Beach",category1, factory);
            user2.addRole(ProgramManagerRole.class, category1, factory);
            user2.getRole(IProgramManagerRole.class).createTeam(project1, factory);
            user3.addRole(DesignerRole.class, category1, factory);
            partecipationRequest = user3.getRole(IDesignerRole.class).createPartecipationRequest(project1.getTeam(), factory);
        } catch (ReflectiveOperationException | RoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddDesigner(){
        try {
            user2.getRole(IProgramManagerRole.class).addDesigner(partecipationRequest);
            assertFalse(project1.getTeam().getPartecipationRequests().contains(partecipationRequest));
            assertTrue(project1.getTeam().getDesigners().contains(user3));
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testRemoveDesigner() throws RoleException {
        ITeam team = user2.getRole(IProgramManagerRole.class).getTeams().stream().findFirst().get();
        team.removeDesigner(user3);
        assertFalse(project1.getTeam().getDesigners().contains(user3));
    }

}