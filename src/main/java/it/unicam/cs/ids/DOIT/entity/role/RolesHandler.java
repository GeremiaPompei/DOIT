package it.unicam.cs.ids.DOIT.entity.role;

import it.unicam.cs.ids.DOIT.entity.Category;
import it.unicam.cs.ids.DOIT.entity.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RolesHandler {
    @Id
    @Column(name = "id_roles_handler")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "id_user")
    @OneToOne
    private User user;
    @JoinColumn(name = "id_project_proposer")
    @OneToOne(cascade = CascadeType.ALL)
    private ProjectProposerRole projectProposerRole;
    @JoinColumn(name = "id_program_manager")
    @OneToOne(cascade = CascadeType.ALL)
    private ProgramManagerRole programManagerRole;
    @JoinColumn(name = "id_designer")
    @OneToOne(cascade = CascadeType.ALL)
    private DesignerRole designerRole;
    @JoinColumn(name = "id_project_manager")
    @OneToOne(cascade = CascadeType.ALL)
    private ProjectManagerRole projectManagerRole;

    public RolesHandler() {
    }

    public RolesHandler(User user) {
        this.user = user;
    }

    public ProjectProposerRole getProjectProposerRole() {
        return projectProposerRole;
    }

    public ProgramManagerRole getProgramManagerRole() {
        return programManagerRole;
    }

    public DesignerRole getDesignerRole() {
        return designerRole;
    }

    public ProjectManagerRole getProjectManagerRole() {
        return projectManagerRole;
    }

    public void addRole(String type, Category category) {
        switch (type) {
            case ProjectProposerRole.TYPE:
                if (isProjectProposer())
                    throw new IllegalArgumentException("The user already has this role!");
                this.projectProposerRole = new ProjectProposerRole(this.user, category);
                break;
            case ProgramManagerRole.TYPE:
                if (isProgramManager())
                    throw new IllegalArgumentException("The user already has this role!");
                this.programManagerRole = new ProgramManagerRole(this.user, category);
                break;
            case DesignerRole.TYPE:
                if (isDesigner())
                    throw new IllegalArgumentException("The user already has this role!");
                this.designerRole = new DesignerRole(this.user, category);
                break;
            case ProjectManagerRole.TYPE:
                if (isProjectManager())
                    throw new IllegalArgumentException("The user already has this role!");
                this.projectManagerRole = new ProjectManagerRole(this.user, category);
                break;
        }
    }

    public void removeRole(String type) {
        switch (type) {
            case ProjectProposerRole.TYPE:
                if (!this.projectProposerRole.getProjects().isEmpty())
                    throw new IllegalArgumentException("This role owns the team!");
                this.projectProposerRole = null;
                break;
            case ProgramManagerRole.TYPE:
                if (!this.programManagerRole.getProjects().isEmpty())
                    throw new IllegalArgumentException("This role owns the team!");
                this.programManagerRole = null;
                break;
            case DesignerRole.TYPE:
                if (!this.designerRole.getProjects().isEmpty())
                    throw new IllegalArgumentException("This role owns the team!");
                this.designerRole = null;
                break;
            case ProjectManagerRole.TYPE:
                if (!this.projectManagerRole.getProjects().isEmpty())
                    throw new IllegalArgumentException("This role owns the team!");
                this.projectManagerRole = null;
                break;
        }
    }

    public boolean isProjectProposer() {
        return this.projectProposerRole != null;
    }

    public boolean isProgramManager() {
        return this.programManagerRole != null;
    }

    public boolean isDesigner() {
        return this.designerRole != null;
    }

    public boolean isProjectManager() {
        return this.projectManagerRole != null;
    }

    public List<Role> getRoles() {
        List<Role> roles = getHandyRoles();
        if (isProjectManager())
            roles.add(projectManagerRole);
        return roles;
    }

    public List<Role> getHandyRoles() {
        List<Role> roles = new ArrayList<>();
        if (isProjectProposer())
            roles.add(projectProposerRole);
        if (isProgramManager())
            roles.add(programManagerRole);
        if (isDesigner())
            roles.add(designerRole);
        return roles;
    }
}
