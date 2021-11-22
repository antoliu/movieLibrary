package it.spindox.movielibrary.repository;

import it.spindox.movielibrary.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IDatabaseRepository extends MongoRepository<Movie, String> {

    @Query("{title:{$regex:'?0', $options:i}}")
    Movie findByTitle(String title);

    @Query("{director:{$regex:'?0', $options:i}}")
    List<Movie> findByDirector(String title);


}
