package it.demo.movie.services;

import it.demo.movie.exceptions.FieldsException;
import it.demo.movie.exceptions.MovieException;
import it.demo.movie.exceptions.RepositoryException;
import it.demo.movie.model.ErrorType;
import it.demo.movie.model.Movie;
import it.demo.movie.repository.IDatabaseRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MovieLibraryService {

    @Autowired
    private IDatabaseRepository repository;


    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies.
     * <p>
     * If the movie list in repository is not empty, the HttpStatus is <code>200</code> and the response body contains the movie list.
     * </p>
     *
     * @return A Response Entity with <strong>movie list</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<Collection<Movie>> getAllMovies() throws RepositoryException {
        if (repository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        List<Movie> movieList = repository.findAll();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Movie</code>.
     * <p>
     * If the specified Id is present in repository, the HttpStatus is <code>200</code> and the response body contains the movie related to the Id.
     * </p>
     *
     * @param id The ID whose associated movie is to be returned
     * @return Response Entity with the<strong>movie</strong> related to the specified ID or, if Id is not present, an <strong>error message</strong>.
     * @throws MovieException      if there is not even a movie with the entered ID
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<Movie> getMovie(String id) throws MovieException, RepositoryException {
        if (repository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        Optional<Movie> result = repository.findById(id);
        if (!result.isPresent()) {
            throw new MovieException(ErrorType.MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Movie movie = result.get();
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies which matches the searched title.
     *
     * @param title The title to be searched
     * @return Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies
     * @throws MovieException      if there is not even a movie with the entered title
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<Movie> getMovieByTitle(String title) throws MovieException, RepositoryException, FieldsException {
        if (repository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        if(StringUtils.isBlank(title)){
            throw new FieldsException(ErrorType.TITLE_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
        Movie movie = repository.findByTitle(title);
        if(movie == null){
            throw new MovieException(ErrorType.TITLE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies which matches the searched director.
     *
     * @param director The director to be searched
     * @return Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies
     * @throws MovieException      if there is not even a movie with the entered director
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<Collection<Movie>> getMovieByDirector(String director) throws MovieException, RepositoryException, FieldsException {
        if (repository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        if(StringUtils.isBlank(director)){
            throw new FieldsException(ErrorType.TITLE_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
        List<Movie> movieList = repository.findByDirector(director);
        if(movieList.isEmpty()){
            throw new MovieException(ErrorType.DIRECTOR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    //TODO documentazione
    public ResponseEntity<Collection<Movie>> getMovieByYear(long year) throws MovieException, RepositoryException, FieldsException {
        if (repository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        if(year < 1900 || year > 2050){
            throw new FieldsException(ErrorType.YEAR_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
        List<Movie> movieList = repository.findByYear(year);
        if(movieList.isEmpty()){
            throw new MovieException(ErrorType.YEAR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    /**
     * Add a movie to the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the Id of the movie to be inserted is not present, the HttpStatus is <code>200</code> and the response body contains a confirmation message
     * </p>
     *
     * @param movie The movie to be insert
     * @return A Response Entity with <strong>confirmation message</strong>
     */
    public ResponseEntity<String> addMovie(Movie movie) {
        repository.save(movie);
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /**
     * Add a <code>List</code> of movie to the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the list is not empty and the movies are, the HttpStatus is <code>200</code> and the response body contains a confirmation message.
     * </p>
     *
     * @param movies The list of movies to be insert
     * @return A Response Entity with <strong>confirmation message</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws FieldsException if the body of request is not valid. If title or director of a movie are null, blank or empty.
     */
    public ResponseEntity<String> addMovies(Collection<Movie> movies) throws FieldsException {
        if (movies.isEmpty()) {
            throw new FieldsException(ErrorType.EMPTY_FIELD, HttpStatus.BAD_REQUEST);
        }
        repository.saveAll(movies);
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /**
     * Deletes a movie from the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the Id of the movie to be deleted is present, the HttpStatus is <code>200</code> and the response body contains a confirmation message.
     * </p>
     *
     * @param id The ID whose associated movie is to be removed
     * @return Response Entity with <strong>confirmation message</strong> or, if list is empty, an <strong>error message</strong>.
     */
    public ResponseEntity<String> deleteMovie(String id) throws MovieException {
        if(!repository.existsById(id)){
            throw new MovieException(ErrorType.MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /**
     * Updates a movie in the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the Id of the movie to be updated is present, the HttpStatus is <code>200</code> and the response body contains a confirmation message.
     * </p>
     *
     * @param id    The ID whose associated movie is to be removed
     * @param movie The Movie with the fields to be update
     * @return A Response Entity with <strong>confirmation message</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws MovieException      if there is not even a movie with the entered ID
     */
    public ResponseEntity<String> updateMovie(String id, Movie movie) throws MovieException {
        if(!repository.existsById(id)){
            throw new MovieException(ErrorType.MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Optional<Movie> element = repository.findById(id);
        Movie oldMovie = element.get();
        oldMovie.setTitle(movie.getTitle());
        oldMovie.setDirector(movie.getDirector());
        repository.save(oldMovie);
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

}
