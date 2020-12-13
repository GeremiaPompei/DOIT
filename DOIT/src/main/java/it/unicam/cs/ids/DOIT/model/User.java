package it.unicam.cs.ids.DOIT.model;

import java.util.List;
import java.util.Map;

public class User extends Searcher {
	private int id;
	private String name;
	private String surname;
	private List<String> generalities;
	private RolesHandler rolesHandler;

	public User(int id, String name, String surname, List<String> generalities) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.generalities = generalities;
		this.rolesHandler = new RolesHandler();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public List<String> getGeneralities() {
		return generalities;
	}

	public RolesHandler getRolesHandler() {
		return rolesHandler;
	}

	//TODO

	private Map<Class, Role> roles;

	public void addRole(Role role) {
		this.roles.put(role.getClass(), role);

	}

	public <T extends Role> T getRole(Class clas) {
		return (T) this.roles.get(clas);
	}
}