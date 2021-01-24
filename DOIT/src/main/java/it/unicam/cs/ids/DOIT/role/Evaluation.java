package it.unicam.cs.ids.DOIT.role;

import javax.persistence.*;

@Entity
public class Evaluation {
    @Id
    @Column(name = "ID_Evaluation")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "ID_Team")
    private Team team;
    private Integer evaluate;

    public Evaluation(Team team, Integer evaluate) {
        this.team = team;
        this.evaluate = evaluate;
    }

    public Team getTeam() {
        return team;
    }

    public Integer getEvaluate() {
        return evaluate;
    }
}
