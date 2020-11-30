package it.unicam.cs.ids.DOIT;

import java.util.List;

public class Designer implements IRole{
    private List<String> cv;
    private User user;

    public Designer(List<String> cv) {
        this.cv = cv;
    }

    public void createPartecipationRequest(Team team) {
        DB.TOTAL_PAERTECIPATIONREQUEST.add(new PartecipationRequest(user, team));
    }

    public List<String> getCv() {
        return cv;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public List<Project> getProjects() {
        return null;
    }
}
