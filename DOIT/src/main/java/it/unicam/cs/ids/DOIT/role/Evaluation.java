package it.unicam.cs.ids.DOIT.role;

import it.unicam.cs.ids.DOIT.project.Project;

import javax.persistence.*;

@Entity
public class Evaluation {
    @Id
    @Column(name = "ID_Evaluation")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "ID_Project")
    private Project project;
    private Integer evaluate;

    public Evaluation(Project project, Integer evaluate) {
        this.project = project;
        this.evaluate = evaluate;
    }

    public Project getTeam() {
        return project;
    }

    public Integer getEvaluate() {
        return evaluate;
    }
}
