package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.user.IUser;

import java.util.Set;

public interface IRole {

    IUser getUser();

    Set<ITeam> getTeams();

    void enterTeam(int idProject);

    void exitTeam(int idProject);

    Set<ICategory> getCategories();

    void addCategory(String idCategory);

    void removeCategory(String idCategory);

    Set<ITeam> getHystory();
}
