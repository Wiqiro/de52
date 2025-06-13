package fr.utbm.entities;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class Actor extends PanacheEntity {
    public String firstName;
    public String lastName;

    @ManyToMany
    public List<Movie> movies;
}
