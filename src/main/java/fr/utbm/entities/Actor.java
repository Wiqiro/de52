package fr.utbm.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACTOR")
public class Actor extends PanacheEntity {

    @Column(name = "LASTNAME", length = 20, nullable = false)
    public String lastname;

    @Column(name = "FIRSTNAME", length = 20)
    public String firstname;

    /** Films où cet acteur joue le rôle principal */
    @OneToMany(mappedBy = "mainActor")
    public List<Movie> mainMovies;

    /** Films où cet acteur est “second rôle” */
    @ManyToMany(mappedBy = "secondaryActors")
    public List<Movie> moviesAsSecondary;
}
