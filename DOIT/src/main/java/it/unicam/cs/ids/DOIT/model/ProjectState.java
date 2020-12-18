package it.unicam.cs.ids.DOIT.model;

public enum ProjectState {
	INITIALIZATION(0),
	INPROGRESS(1),
	FINISHED(2);

	private int num;

	ProjectState(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}
}