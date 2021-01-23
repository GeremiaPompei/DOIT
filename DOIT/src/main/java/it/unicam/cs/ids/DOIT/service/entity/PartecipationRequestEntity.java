package it.unicam.cs.ids.DOIT.service.entity;

import it.unicam.cs.ids.DOIT.partecipation_request.IPartecipationRequest;
import it.unicam.cs.ids.DOIT.role.IPendingRole;
import it.unicam.cs.ids.DOIT.role.IRole;
import it.unicam.cs.ids.DOIT.service.ServicesHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Entity
public class PartecipationRequestEntity implements ResourceEntity<IPartecipationRequest> {

    @Transient
    @Autowired
    private ServicesHandler servicesHandler;
    @Id
    private String id;
    private boolean state;
    private String description;
    private String dateTime;

    @Override
    public void fromObject(IPartecipationRequest iPartecipationRequest) {
        this.state = iPartecipationRequest.getState();
        this.description = iPartecipationRequest.getDescription();
        this.dateTime = iPartecipationRequest.getDateTime().toString();
        this.id = iPartecipationRequest.getPendingRole().getUser().getId() + " "
                + iPartecipationRequest.getTeam().getId() + " " + iPartecipationRequest.getPendingRole().getClass().getName();
    }

    @Override
    public IPartecipationRequest toObject() throws Exception {
        String[] params = this.id.split(" ");
        Class clazz = Class.forName(params[2]);
        IPartecipationRequest pr = servicesHandler.getFactoryModel().createPartecipationRequest(
                (IPendingRole) servicesHandler.getResourceHandler().getUser(Long.parseLong(params[0])).getRole(clazz),
                servicesHandler.getResourceHandler().getProject(Long.parseLong(params[1])).getTeam()
        );
        pr.setDateTime(LocalDateTime.parse(this.dateTime));
        pr.setDescription(this.description);
        pr.setState(this.state);
        return pr;
    }
}
