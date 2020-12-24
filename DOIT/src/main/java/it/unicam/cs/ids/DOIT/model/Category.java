package it.unicam.cs.ids.DOIT.model;

public class Category {
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