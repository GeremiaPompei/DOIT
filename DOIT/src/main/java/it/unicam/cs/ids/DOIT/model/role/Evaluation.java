package it.unicam.cs.ids.DOIT.model.role;

import javax.persistence.*;

@Entity
public class Evaluation {
    @Id
    @Column(name = "ID_Evaluation")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long projectId;
    private Integer evaluate;

    public Evaluation(Long projectId, Integer evaluate) {
        this.projectId = projectId;
        this.evaluate = evaluate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Integer getEvaluate() {
        return evaluate;
    }
}
