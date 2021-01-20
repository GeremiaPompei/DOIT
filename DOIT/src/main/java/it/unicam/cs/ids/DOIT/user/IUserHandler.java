package it.unicam.cs.ids.DOIT.user;


public interface IUserHandler {
    IUser logIn(String email, String password);

    void signIn(String name, String surname, String birthDate, String sex, String email, String password);

    void logOut(int idUser, int token);

    IUser getUser(int idUser, int token);

    IUser getUser();
}
