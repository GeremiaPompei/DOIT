package it.unicam.cs.ids.DOIT.simple.model;

import it.unicam.cs.ids.DOIT.domain.model.ICategory;

public class Category implements ICategory {
	private String name;
	private String description;

	public Category(String name, String description) {
		this.name = name.toUpperCase();
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Category{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}