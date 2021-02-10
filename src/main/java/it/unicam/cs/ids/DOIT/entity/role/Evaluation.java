package it.unicam.cs.ids.DOIT.entity.role;

import javax.persistence.*;

@Entity
public class Evaluation {
    @Id
    @Column(name = "id_evaluation")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long projectId;
    private Integer evaluation;

    public Evaluation(Long projectId, Integer evaluate) {
        this.projectId = projectId;
        this.evaluation = evaluate;
    }

    public Evaluation() {
    }

    public Long getProjectId() {
        return projectId;
    }

    public Integer getEvaluation() {
        return evaluation;
    }
}
