package it.demo.movie.services;

import it.demo.movie.exceptions.EntityException;
import it.demo.movie.exceptions.FieldsException;
import it.demo.movie.exceptions.RepositoryException;
import it.demo.movie.model.*;
import it.demo.movie.repository.IDatabaseCinemaRepository;
import it.demo.movie.repository.IDatabaseMovieRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieLibraryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieLibraryService.class);

    @Autowired
    private IDatabaseMovieRepository movieRepository;

    @Autowired
    private IDatabaseCinemaRepository cinemaRepository;

    /* ----- MOVIE REPOSITORY ----- */

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies.
     * <p>
     * If the movie list in repository is not empty, the HttpStatus is <code>200</code> and the response body contains the movie list.
     * </p>
     *
     * @return A Response Entity with <strong>movie list</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws RepositoryException  if the movie repository is empty
     */
    public ResponseEntity<Collection<Movie>> getAllMovies() throws RepositoryException {
        LOGGER.info("Start process: getAllMovies");
        long start = System.currentTimeMillis();
        if (movieRepository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY);
        }
        List<Movie> movieList = movieRepository.findAll();
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: getAllMovies {(" + (stop - start) + ")} ");
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Movie</code>.
     * <p>
     * If the specified Id is present in repository, the HttpStatus is <code>200</code> and the response body contains the movie related to the Id.
     * </p>
     *
     * @param id The ID whose associated movie is to be returned
     * @return Response Entity with the<strong>movie</strong> related to the specified ID or, if ID is not present, an <strong>error message</strong>.
     * @throws EntityException      if there is not even a movie with the entered ID
     * @throws RepositoryException  if the movie repository is empty
     * */
    public ResponseEntity<Movie> getMovie(String id) throws EntityException, RepositoryException {
        LOGGER.info("Start process: getMovie");
        long start = System.currentTimeMillis();
        if (movieRepository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY);
        }
        Optional<Movie> result = movieRepository.findById(id);
        if (!result.isPresent()) {
            throw new EntityException(ErrorType.MOVIE_NOT_FOUND);
        }
        Movie movie = result.get();
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: getMovie {(" + (stop - start) + ")} ");
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies which matches the searched title.
     *
     * @param title The title to be searched
     * @return Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies
     * @throws EntityException      if there is not even a movie with the entered title
     * @throws RepositoryException  if the movie repository is empty
     * @throws FieldsException      if the inserted title is null, empty or blank
     */
    public ResponseEntity<Collection<Movie>> getMovieByTitle(String title) throws EntityException, RepositoryException, FieldsException {
        LOGGER.info("Start process: getMovieByTitle");
        long start = System.currentTimeMillis();
        if (movieRepository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY);
        }
        if(StringUtils.isBlank(title)){
            throw new FieldsException(ErrorType.TITLE_NOT_VALID);
        }
        List<Movie> movieList = movieRepository.findByTitle(title);
        if(movieList.isEmpty()){
            throw new EntityException(ErrorType.TITLE_NOT_FOUND);
        }
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: getMovieByTitle {(" + (stop - start) + ")} ");
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies which matches the searched director.
     *
     * @param director The director to be searched
     * @return Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies
     * @throws EntityException      if there is not even a movie with the entered director
     * @throws RepositoryException  if the movie repository is empty
     * @throws FieldsException      if the inserted director is null, empty or blank
     */
    public ResponseEntity<Collection<Movie>> getMovieByDirector(String director) throws EntityException, RepositoryException, FieldsException {
        LOGGER.info("Start process: getMovieByDirector");
        long start = System.currentTimeMillis();
        if (movieRepository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY);
        }
        if(StringUtils.isBlank(director)){
            throw new FieldsException(ErrorType.DIRECTOR_NOT_VALID);
        }
        List<Movie> movieList = movieRepository.findByDirector(director);
        if(movieList.isEmpty()){
            throw new EntityException(ErrorType.DIRECTOR_NOT_FOUND);
        }
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: getMovieByDirector {(" + (stop - start) + ")} ");
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    /**
     * Add a movie to the movie repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the Id of the movie to be inserted is not present, the HttpStatus is <code>200</code> and the response body contains a confirmation message
     * </p>
     *
     * @param movie The movie to be inserted
     * @return A Response Entity with <strong>confirmation message</strong>
     */
    public ResponseEntity<String> addMovie(Movie movie) {
        LOGGER.info("Start process: addMovie");
        long start = System.currentTimeMillis();
        movieRepository.save(movie);
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: addMovie {(" + (stop - start) + ")} ");
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /**
     * Add a <code>List</code> of movie to the movie repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the list is not empty and the movies are added, the HttpStatus is <code>200</code> and the response body contains a confirmation message.
     * </p>
     *
     * @param movies The list of movies to be inserted
     * @return A Response Entity with <strong>confirmation message</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws FieldsException  if the movie list is empty.
     */
    public ResponseEntity<String> addMovies(Collection<Movie> movies) throws FieldsException {
        LOGGER.info("Start process: addMovies");
        long start = System.currentTimeMillis();
        if (movies.isEmpty()) {
            throw new FieldsException(ErrorType.EMPTY_FIELD);
        }
        movieRepository.saveAll(movies);
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: addMovies {(" + (stop - start) + ")} ");
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
     * @throws EntityException      if there is not even a movie with the entered ID
     */
    public ResponseEntity<String> deleteMovie(String id) throws EntityException {
        LOGGER.info("Start process: deleteMovie");
        long start = System.currentTimeMillis();
        if(!movieRepository.existsById(id)){
            throw new EntityException(ErrorType.MOVIE_NOT_FOUND);
        }
        movieRepository.deleteById(id);
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: deleteMovie {(" + (stop - start) + ")} ");
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /**
     * Updates a movie in the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the Id of the movie to be updated is present, the HttpStatus is <code>200</code> and the response body contains a confirmation message.
     * </p>
     *
     * @param id    The ID whose associated movie is to be removed
     * @param movie The Movie with the fields to be updated
     * @return A Response Entity with <strong>confirmation message</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws EntityException      if there is not even a movie with the entered ID
     */
    public ResponseEntity<String> updateMovie(String id, Movie movie) throws EntityException {
        LOGGER.info("Start process: updateMovie");
        long start = System.currentTimeMillis();
        Optional<Movie> element = movieRepository.findById(id);
        if(!element.isPresent()){
            throw new EntityException(ErrorType.MOVIE_NOT_FOUND);
        }
        Movie oldMovie = element.get();

        oldMovie.setTitle(movie.getTitle());
        oldMovie.setDirector(movie.getDirector());
        oldMovie.setGenre(movie.getGenre());
        oldMovie.setYear(movie.getYear());

        movieRepository.save(oldMovie);
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: updateMovie {(" + (stop - start) + ")} ");
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /* ---------- CINEMA COLLECTION ---------- */

    public ResponseEntity<Collection<Cinema>> getAllCinemas() throws RepositoryException {
        LOGGER.info("Start process: getAllCinemas");
        long start = System.currentTimeMillis();
        if (cinemaRepository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY);
        }
        List<Cinema> cinemaList = cinemaRepository.findAll();
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: getAllCinemas {(" + (stop - start) + ")} ");
        return new ResponseEntity<>(cinemaList, HttpStatus.OK);
    }

    public ResponseEntity<Cinema> getCinema(String id) throws RepositoryException, EntityException {
        if (cinemaRepository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY);
        }
        Optional<Cinema> result = cinemaRepository.findById(id);
        if (!result.isPresent()) {
            throw new EntityException(ErrorType.CINEMA_NOT_FOUND);
        }
        Cinema cinema = result.get();
        return new ResponseEntity<>(cinema, HttpStatus.OK);
    }

    public ResponseEntity<String> addCinema(Cinema cinema) {
        cinemaRepository.save(cinema);
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCinema(String id) throws EntityException {
        LOGGER.info("Start process: deleteCinema");
        long start = System.currentTimeMillis();
        if(!cinemaRepository.existsById(id)){
            throw new EntityException(ErrorType.CINEMA_NOT_FOUND);
        }
        cinemaRepository.deleteById(id);
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: deleteCinema {(" + (stop - start) + ")} ");
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    public ResponseEntity<String> updateCinema(String id, Cinema cinema) throws EntityException {
        LOGGER.info("Start process: updateCinema");
        long start = System.currentTimeMillis();
        Optional<Cinema> element = cinemaRepository.findById(id);
        if(!element.isPresent()){
            throw new EntityException(ErrorType.CINEMA_NOT_FOUND);
        }
        Cinema oldCinema = element.get();

        oldCinema.setName(cinema.getName());
        oldCinema.setCity(cinema.getCity());
        oldCinema.setProjections(cinema.getProjections());

        cinemaRepository.save(oldCinema);
        long stop = System.currentTimeMillis();
        LOGGER.info("Stop process: updateCinema {(" + (stop - start) + ")} ");
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    public ResponseEntity<Collection<Cinema>> getCinemaByMovie(String id) throws RepositoryException, EntityException {
        if (cinemaRepository.count() == 0) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY);
        }
        Optional<Movie> result = movieRepository.findById(id);
        if(!result.isPresent()){
            throw new EntityException(ErrorType.MOVIE_NOT_FOUND);
        }
        Movie movie = result.get();
        List<Cinema> cinemas = cinemaRepository.findAll().stream()
                .filter(cinema -> cinema.getProjections().stream().anyMatch(projection -> projection.getMovie().equals(movie)))
                .collect(Collectors.toList());

        if(cinemas.isEmpty()){
            throw new EntityException(ErrorType.CINEMA_PROJECTIONS_NOT_FOUND);
        }
        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }
}
