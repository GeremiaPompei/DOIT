package it.unicam.cs.ids.DOIT.simple.model.roles;

import it.unicam.cs.ids.DOIT.domain.model.*;
import it.unicam.cs.ids.DOIT.domain.model.roles.IProjectManagerRole;
import it.unicam.cs.ids.DOIT.simple.model.roles.initial.DesignerRole;
import it.unicam.cs.ids.DOIT.simple.model.Role;

import java.util.Set;
import java.util.function.Function;

public class ProjectManagerRole extends Role implements IProjectManagerRole {

    public ProjectManagerRole(IUser user, ICategory category, IFactoryModel factoryModel) {
        super(user, category, factoryModel);
    }

    public Function<Set<IProjectState>, IProjectState> upgradeState(IProject project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        return  pr-> pr.stream().filter(p->p.getId()==project.getId()+1).findAny().orElseThrow(null);
    }

    public Function<Set<IProjectState>, IProjectState> downgradeState(IProject project) throws Exception {
        if (!this.getProjects().contains(project))
            throw new IllegalArgumentException("Il Project Manager non possiede il progetto [" + project.getId() + "]!");
        return pr-> pr.stream().filter(p->p.getId()==project.getId()-1).findAny().orElseThrow(null);

    }

    @Override
    public void insertEvaluation(IProject project, int evaluation, IUser designer) throws RoleException {
        designer.getRole(DesignerRole.class).enterEvaluation(project,evaluation);
    }

    public void turnProjectOff(IProject project, IRole role) throws RoleException {
       role.exitProject(project);
    }


}
