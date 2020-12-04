package it.unicam.cs.ids.DOIT;

public enum ProjectState {
    INITIAL("INITIAL","Is the state..."),
    INPROGRESS("INPROGRESS","Is the state..."),
    FINISH("FINISH","Is the state...");

    String name;
    String description;

    ProjectState(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
