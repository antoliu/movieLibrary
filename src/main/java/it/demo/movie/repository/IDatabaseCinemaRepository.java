package it.demo.movie.repository;


import it.demo.movie.model.Cinema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IDatabaseCinemaRepository extends MongoRepository<Cinema, String> {



}
