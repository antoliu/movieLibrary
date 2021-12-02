package it.demo.movie.repository;

import it.demo.movie.model.Projection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IDatabaseProjectionRepository extends MongoRepository<Projection, String> {



}
