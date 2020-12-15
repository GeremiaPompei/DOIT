package it.unicam.cs.ids.DOIT.model;

import java.util.List;

public abstract class Searcher {

	public List<Project> searchProject(Category category) {
		return null;
	}
	public <T extends Role> List<User> searchUser(Class<T> clazz, Category category) {
		return null;
	}
}