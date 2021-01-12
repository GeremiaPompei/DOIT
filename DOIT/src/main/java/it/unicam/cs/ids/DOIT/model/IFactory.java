package it.unicam.cs.ids.DOIT.model;

public interface IFactory {
    IProject createProject(int id, String name, String description, IUser projectProposer, ICategory category);
    ICategory createCategory(String name, String description);
    IProjectState createProjectState(int id, String name, String description);
    IUser createUser(int id, String name, String surname, int birthdDay, String sex);
    IPartecipationRequest createPartecipationRequest(IUser user, ITeam team);
    ITeam createTeam(IProject project, IUser user);
    <T extends IRole> T createRole(Class<T> clazz, IUser user, ICategory category) throws ReflectiveOperationException;
}
