package it.unicam.cs.ids.DOIT.user;

import it.unicam.cs.ids.DOIT.category.ICategory;
import it.unicam.cs.ids.DOIT.role.*;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;

import java.util.HashSet;
import java.util.Set;

public class UserHandler implements IUserHandler {

    private IUser user;
    private Set<Class<? extends IRole>> choosableRoles;

    public UserHandler() {
        choosableRoles = new HashSet<>();
        choosableRoles.add(ProgramManagerRole.class);
        choosableRoles.add(ProjectProposerRole.class);
        choosableRoles.add(DesignerRole.class);
        choosableRoles.add(ProjectManagerRole.class);
    }

    @Override
    public void login(int id) {
        user = ServicesHandler.getInstance().getResourceHandler().getUser(id);
        if(user==null)
            throw new NullPointerException("Utente non trovato!");
    }

    @Override
    public void signIn(String name, String surname, String birthDate, String sex) {
        ServicesHandler.getInstance().getFactoryModel().createUser(name, surname, birthDate, sex);
    }

    public void addRole(String roleName, String idCategory) throws Exception {
        ICategory category = ServicesHandler.getInstance().getResourceHandler().getCategory(idCategory);
        if (category == null)
            throw new Exception("Categoria inesistente!");
        if (roleName.equalsIgnoreCase("ProjectManagerRole"))
            throw new Exception();
        user.addRole(getRole(roleName), category.getName());
    }

    public void removeRole(String roleName) {
        user.removeRole(getRole(roleName));
    }


    public Class<? extends IRole> getRole(String roleName) {
        return choosableRoles.stream().filter(c -> c.getSimpleName().equalsIgnoreCase(roleName)).findAny().orElse(null);
    }

    public Set<Class<? extends IRole>> getChoosableRoles() {
        return choosableRoles;
    }

    @Override
    public void logOut() {
        user = null;
    }

    @Override
    public IUser getUser() {
        return user;
    }
}
