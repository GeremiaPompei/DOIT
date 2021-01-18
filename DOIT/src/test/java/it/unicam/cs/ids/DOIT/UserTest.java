
package it.unicam.cs.ids.DOIT;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.role.ProjectManagerRole;
import it.unicam.cs.ids.DOIT.role.ProjectProposerRole;
import it.unicam.cs.ids.DOIT.role.DesignerRole;
import it.unicam.cs.ids.DOIT.role.ProgramManagerRole;
import it.unicam.cs.ids.DOIT.service.*;
import it.unicam.cs.ids.DOIT.user.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private IUser user1;
    private IUser user2;
    private ICategory category;
    private IFactoryModel factory;
    private IResourceHandler resourceHandler;

    @BeforeEach
    private void init() {
        ServicesHandler.getInstance().getResourceHandler().getRisorse().clear();
        category = ServicesHandler.getInstance().getFactoryModel().createCategory("Sport", "Descrizione.");
        user1 = ServicesHandler.getInstance().getFactoryModel().createUser("Saverio", "Tommasi", "1998", "Male");
        user2 = ServicesHandler.getInstance().getFactoryModel().createUser("Mario", "Fartade", "2000", "Male");
    }

    @Test
    void addRole() {
        assertDoesNotThrow(() -> this.user1.addRole(ProjectProposerRole.class, category.getName()));
        assertDoesNotThrow(() -> this.user1.addRole(DesignerRole.class, category.getName()));
        assertDoesNotThrow(() -> this.user1.addRole(ProgramManagerRole.class, category.getName()));

    }

    @Test
    void getRole() {
        assertThrows(RoleException.class, () -> this.user1.getRole(ProjectProposerRole.class));
        assertDoesNotThrow(() -> this.user1.addRole(ProjectProposerRole.class, category.getName()));
        assertDoesNotThrow(() -> this.user1.getRole(ProjectProposerRole.class));
    }

    @Test
    void removeRole() {
        assertDoesNotThrow(() -> this.user1.addRole(ProjectProposerRole.class, category.getName()));
        assertDoesNotThrow(() -> this.user1.getRole(ProjectProposerRole.class));
        assertThrows(RoleException.class, ()->{ user1.getRole(ProjectManagerRole.class); });
        this.user1.removeRole(ProjectProposerRole.class);
        assertThrows(RoleException.class, () -> this.user1.getRole(ProjectProposerRole.class));
    }
}
