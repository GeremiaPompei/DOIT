package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.Category;
import it.unicam.cs.ids.DOIT.service.IFactoryModel;
import it.unicam.cs.ids.DOIT.user.User;

import javax.persistence.*;

@Entity
public class RolesHandler {
    @Transient
    private IFactoryModel factoryModel;
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

    public RolesHandler(User user, IFactoryModel factoryModel) {
        this.user = user;
        this.factoryModel = factoryModel;
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
        this.projectProposerRole = factoryModel.createProjectProposerRole(this.user, category);
    }

    public void addProgramManagerRole(Category category) {
        this.programManagerRole = factoryModel.createProgramManagerRole(this.user, category);
    }

    public void addDesignerRole(Category category) {
        this.designerRole = factoryModel.createDesignerRole(this.user, category);
    }

    public void addProjectManagerRole(Category category) {
        this.projectManagerRole = factoryModel.createProjectManagerRole(this.user, category);
    }

    public void removeProjectProposerRole() {
        this.projectProposerRole = null;
    }

    public void removeProgramManagerRole() {
        this.programManagerRole = null;
    }

    public void removeDesignerRole() {
        this.designerRole = null;
    }

    public void removeProjectManagerRole() {
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
