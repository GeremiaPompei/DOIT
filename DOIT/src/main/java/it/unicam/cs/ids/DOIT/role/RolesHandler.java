package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class RolesHandler {
    @Id
    @Column(name = "ID_RolesHandler")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "ID_User")
    @OneToOne
    private User user;
    @JoinColumn(name = "ID_ProjectProposer")
    @OneToOne(cascade = CascadeType.ALL)
    private ProjectProposerRole projectProposerRole;
    @JoinColumn(name = "ID_ProgramManager")
    @OneToOne(cascade = CascadeType.ALL)
    private ProgramManagerRole programManagerRole;
    @JoinColumn(name = "ID_Designer")
    @OneToOne(cascade = CascadeType.ALL)
    private DesignerRole designerRole;
    @JoinColumn(name = "ID_ProjectManager")
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

    public void addProjectProposerRole(Category category) {
        this.projectProposerRole = new ProjectProposerRole(this.user, category);
    }

    public void addProgramManagerRole(Category category) {
        this.programManagerRole = new ProgramManagerRole(this.user, category);
    }

    public void addDesignerRole(Category category) {
        this.designerRole = new DesignerRole(this.user, category);
    }

    public void addProjectManagerRole(Category category) {
        this.projectManagerRole = new ProjectManagerRole(this.user, category);
    }

    public void removeProjectProposerRole() {
        if(!this.projectProposerRole.getProjects().isEmpty())
            throw new IllegalArgumentException("Il ruolo contiene team!");
        this.projectProposerRole = null;
    }

    public void removeProgramManagerRole() {
        if(!this.programManagerRole.getProjects().isEmpty())
            throw new IllegalArgumentException("Il ruolo contiene team!");
        this.programManagerRole = null;
    }

    public void removeDesignerRole() {
        if(!this.designerRole.getProjects().isEmpty())
            throw new IllegalArgumentException("Il ruolo contiene team!");
        this.designerRole = null;
    }

    public void removeProjectManagerRole() {
        if(!this.projectManagerRole.getProjects().isEmpty())
            throw new IllegalArgumentException("Il ruolo contiene team!");
        this.projectManagerRole = null;
    }

    public boolean isProjectProposerRole() {
        return this.projectProposerRole != null;
    }

    public boolean isProgramManager() {
        return this.programManagerRole != null;
    }

    public boolean isDesignerRole() {
        return this.designerRole != null;
    }

    public boolean isProjectManager() {
        return this.projectManagerRole != null;
    }
}
