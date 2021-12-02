package it.demo.movie.repository;

import it.demo.movie.model.Genre;
import it.demo.movie.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDatabaseMovieRepository extends MongoRepository<Movie, String> {

    @Query("{title:{$regex:'?0', $options:i}}")
    List<Movie> findByTitle(String title);

    @Query("{director:{$regex:'?0', $options:i}}")
    List<Movie> findByDirector(String title);

    @Query("{year: ?0}")
    List<Movie> findByYear(long year);

    @Query("{genre:'?0'}")
    List<Movie> findByGenre(Genre genre);


    @Query(value="{genre:'?0'}", fields="{'?1'}")
    List<Movie> findByFields(Genre genre, String fields);


}
