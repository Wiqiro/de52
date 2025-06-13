package fr.utbm.services;

import java.util.List;
import java.util.Set;

import fr.utbm.entities.Movie;
import fr.utbm.entities.Actor;
import fr.utbm.repositories.MovieRepository;
import fr.utbm.repositories.ActorRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

@Singleton
public class MovieService {
    @Inject
    MovieRepository movieRepository;

    @Inject
    ActorRepository actorRepository;

    public List<Movie> getMovies() {
        return movieRepository.listAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Transactional
    public Movie addMovie(Movie movie) {
        movieRepository.persist(movie);
        return movie;
    }

    @Transactional
    public boolean deleteMovieById(Long id) {
        return movieRepository.deleteById(id);
    }

    public Set<Actor> getMovieActors(Long movieId) {
        Movie movie = movieRepository.findById(movieId);
        if (movie == null) {
            return null;
        }
        return movie.secondaryActors;
    }

    @Transactional
    public Actor addMovieActor(Long movieId, Actor actor) {
        Movie movie = movieRepository.findById(movieId);

        if (movie == null) {
            return null;
        }

        actorRepository.persist(actor);

        movie.secondaryActors.add(actor);
        movieRepository.persist(movie);

        return actor;
    }

    @Transactional
    public boolean deleteMovieActor(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId);
        Actor actor = actorRepository.findById(actorId);

        if (movie == null || actor == null) {
            return false;
        }

        movie.secondaryActors.remove(actor);
        movieRepository.persist(movie);
        return true;
    }
}
