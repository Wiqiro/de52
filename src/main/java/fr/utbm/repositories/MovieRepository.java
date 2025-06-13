package fr.utbm.repositories;

import fr.utbm.entities.Movie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.inject.Singleton;

@Singleton
public class MovieRepository implements PanacheRepository<Movie> {
}
