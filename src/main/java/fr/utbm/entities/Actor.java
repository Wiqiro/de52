package fr.utbm.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACTOR")
public class Actor extends PanacheEntity {

    @Column(name = "LASTNAME", length = 20, nullable = false)
    public String lastname;

    @Column(name = "FIRSTNAME", length = 20)
    public String firstname;

    @JsonIgnore
    @OneToMany(mappedBy = "mainActor")
    public List<Movie> mainMovies;

    @JsonIgnore
    @ManyToMany(mappedBy = "secondaryActors")
    public List<Movie> moviesAsSecondary;
}
