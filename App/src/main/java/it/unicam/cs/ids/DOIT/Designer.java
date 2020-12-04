package it.unicam.cs.ids.DOIT;

import java.util.ArrayList;
import java.util.List;

public class Designer implements IRole{
    private List<String> cv;
    private User user;
    private List<PartecipationRequest> partecipationRequests;

    public Designer(List<String> cv) {
        this.cv = cv;
        this.partecipationRequests = new ArrayList<>();
    }

    public void createPartecipationRequest(Team team) {
        PartecipationRequest pr = new PartecipationRequest(user, team);
        this.partecipationRequests.add(pr);
        DB.TOTAL_PAERTECIPATIONREQUEST.add(pr);
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

    public User getUser() {
        return user;
    }

    public List<PartecipationRequest> getRefuseReasons() {
        return this.partecipationRequests;
    }
}
