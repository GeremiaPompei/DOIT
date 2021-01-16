package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Role implements IRole {

    private  int id;

    private Set<ITeam> teams;

    private Set<ITeam> hystory;

    private Set<ICategory> categories;

    public Role(int idUser, String idCategory) {
        this.id = idUser;
        teams = new HashSet<>();
        categories = new HashSet<>();
        hystory = new HashSet<>();
        this.categories.add(ServicesHandler.getInstance().getResourceHandler().getCategory(idCategory));
    }

    public int getId() {
        return this.id;
    }

    public Set<ITeam> getTeams() {
        return teams;
    }

    public Set<ICategory> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Role{" +
                "team=" + teams.stream().map(p -> p.getProject().getId()).collect(Collectors.toSet()) +
                ", categories=" + categories.stream().map(p -> p.getName()).collect(Collectors.toSet()) +
                '}';
    }

    @Override
    public void exitTeam(int idProject) {
        ITeam team = ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam();
        hystory.add(team);
        teams.remove(team);
    }

    @Override
    public void enterTeam(int idProject) {
        teams.add(ServicesHandler.getInstance().getResourceHandler().getProject(idProject).getTeam());
    }

    public Set<ITeam> getHystory() {
        return hystory;
    }

    @Override
    public void addCategory(String idCategory) {
        ICategory category = ServicesHandler.getInstance().getResourceHandler().getCategory(idCategory);
        this.categories.add(category);
    }

    @Override
    public void removeCategory(String idCategory) {
        ICategory category = ServicesHandler.getInstance().getResourceHandler().getCategory(idCategory);
        if (this.categories.size() == 1)
            throw new IllegalArgumentException("Non si puo eliminare una categoria quando ne rimane solo una!");
        if (this.teams.stream().filter(p -> p.getProject().getCategory().equals(category)).findAny().orElse(null) != null)
            throw new IllegalArgumentException("Non puo essere eliminata una categoria in uso su uno dei progetti appartenenti al ruolo!");
        this.categories.remove(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(this.getClass(), o.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass());
    }
}