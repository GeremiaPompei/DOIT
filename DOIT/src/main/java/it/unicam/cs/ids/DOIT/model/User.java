package it.unicam.cs.ids.DOIT.model;

import java.util.List;

public class User extends Searcher {
	private int id;
	private String name;
	private String surname;
	private List<String> generalities;
	public RolesHandler rolesHandler;

	public User(int id, String name, String surname, String[] generalities) {
		throw new UnsupportedOperationException();
	}
}