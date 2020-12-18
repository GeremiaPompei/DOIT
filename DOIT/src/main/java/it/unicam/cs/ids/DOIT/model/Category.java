package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
}