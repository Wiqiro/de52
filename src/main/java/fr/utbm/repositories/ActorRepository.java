package fr.utbm.repositories;

import fr.utbm.entities.Actor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.inject.Singleton;

@Singleton
public class ActorRepository implements PanacheRepository<Actor> {

}
