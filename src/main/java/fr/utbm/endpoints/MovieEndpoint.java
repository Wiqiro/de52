package fr.utbm.endpoints;

import java.util.List;
import java.util.Set;

import fr.utbm.entities.Movie;
import fr.utbm.entities.Actor;
import fr.utbm.services.MovieService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movies")
public class MovieEndpoint {

    @Inject
    MovieService movieService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(movie).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovie(Movie movie) {
        Movie createdMovie = movieService.addMovie(movie);
        return Response.status(Response.Status.CREATED).entity(createdMovie).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteMovieById(@PathParam("id") Long id) {
        boolean deleted = movieService.deleteMovieById(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/{id}/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieActors(@PathParam("id") Long movieId) {
        Set<Actor> actors = movieService.getMovieActors(movieId);
        if (actors == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(actors).build();
    }
    
    @POST
    @Path("/{movieId}/actors")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMovieActor(@PathParam("movieId") Long movieId, Actor actor) {
        Actor createdActor = movieService.addMovieActor(movieId, actor);
        if (createdActor != null) {
            return Response.status(Response.Status.CREATED).entity(createdActor).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @DELETE
    @Path("/{movieId}/actors/{actorId}")
    public Response deleteMovieActor(@PathParam("movieId") Long movieId, @PathParam("actorId") Long actorId) {
        boolean deleted = movieService.deleteMovieActor(movieId, actorId);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
