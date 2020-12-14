package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectState {
	private String name;
	private String description;
	public List<Project> projects = new ArrayList<>();
	public List<CurriculumVitae> cv = new ArrayList<>();
	public ProjectManagerRole projectManagerRole;
}