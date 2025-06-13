package fr.utbm.entities;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
public class Movie extends PanacheEntity {
    public String title;
    public Integer copies;
    public String movieType; // TODO: replace with enum ?
    public Integer mainActorId;

    @ManyToMany
    @JoinTable(name = "MOVIE_SEC_ACTORS", joinColumns = @JoinColumn(name = "ID_MOVIE"), inverseJoinColumns = @JoinColumn(name = "ID_ACTOR"))
    public List<Actor> actors;
}
