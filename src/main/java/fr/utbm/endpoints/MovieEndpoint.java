package fr.utbm.endpoints;

import fr.utbm.repositories.MovieRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/movies")
public class MovieEndpoint {

    @Inject
    MovieRepository movieRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMovies() {
        return movieRepository.listAll().toString();
    }
}
