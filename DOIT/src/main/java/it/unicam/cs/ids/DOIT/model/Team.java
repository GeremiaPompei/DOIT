package it.unicam.cs.ids.DOIT.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private boolean state;
	private int id;
	public Project project;
	public ProgramManager programManager;
	public List<Designer> _ha = new ArrayList<>();
}