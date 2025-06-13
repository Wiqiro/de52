package fr.utbm.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MOVIE")
public class Movie extends PanacheEntity {

    @Column(name = "TITLE", length = 50, nullable = false)
    public String title;

    @Column(name = "COPIES", nullable = false)
    public Integer copies;

    @Column(name = "MOVIE_TYPE", length = 20, nullable = false)
    public String movieType;   // On garde une chaîne pour rester simple.

    /* --- Relations --- */

    /** Acteur principal (FK MAIN_ACTOR_ID) */
    @ManyToOne
    @JoinColumn(name = "MAIN_ACTOR_ID")
    public Actor mainActor;

    /** Acteurs secondaires via la table VIDEO.MOVIE_SEC_ACTORS */
    @ManyToMany
    @JoinTable(
        name = "MOVIE_SEC_ACTORS",
        joinColumns        = @JoinColumn(name = "ID_MOVIE"),
        inverseJoinColumns = @JoinColumn(name = "ID_ACTOR")
    )
    public Set<Actor> secondaryActors = new HashSet<>();
}
