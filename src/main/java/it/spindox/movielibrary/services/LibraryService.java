package it.spindox.movielibrary.services;

import it.spindox.movielibrary.exceptions.FieldsException;
import it.spindox.movielibrary.exceptions.MovieException;
import it.spindox.movielibrary.exceptions.RepositoryException;
import it.spindox.movielibrary.model.ErrorType;
import it.spindox.movielibrary.model.Movie;
import it.spindox.movielibrary.repository.IDatabaseRepository;
import it.spindox.movielibrary.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private IDatabaseRepository dbRepository;

    @Autowired
    private MovieRepository repository;

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies.
     * <p>
     * If the movie list in repository is not empty, the HttpStatus is <code>200</code> and the response body contains the movie list.
     * </p>
     * @return A Response Entity with <strong>movie list</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<Collection<Movie>> getAllMovies() throws RepositoryException {
        Collection<Movie> movieList = repository.getAllMovies();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    /**
     * Add a movie to the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the Id of the movie to be inserted is not present, the HttpStatus is <code>200</code> and the response body contains a confirmation message
     * </p>
     * @param movie     The movie to be insert
     * @return A Response Entity with <strong>confirmation message</strong>
     * @throws FieldsException if the title or the director fields are null or empty or blank.
     */
    public ResponseEntity<String> addMovie(Movie movie) throws FieldsException {
        dbRepository.save(movie);
        repository.checkFields(movie);
        repository.addMovie(movie);
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /**
     * Add a <code>List</code> of movie to the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the list is not empty and the movies are, the HttpStatus is <code>200</code> and the response body contains a confirmation message.
     * </p>
     * @param movies    The list of movies to be insert
     * @return A Response Entity with <strong>confirmation message</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws FieldsException if the body of request is not valid. If title or director of a movie are null, blank or empty.
     */
    public ResponseEntity<String> addMovies(Collection<Movie> movies) throws FieldsException {
        if(movies.isEmpty()){
            throw new FieldsException(ErrorType.EMPTY_FIELD, HttpStatus.BAD_REQUEST);
        }
        for (Movie movie: movies) {
            repository.checkFields(movie);
            repository.addMovie(movie);
        }
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Movie</code>.
     * <p>
     * If the specified Id is present in repository, the HttpStatus is <code>200</code> and the response body contains the movie related to the Id.
     * </p>
     * @param id    The ID whose associated movie is to be returned
     * @return Response Entity with the<strong>movie</strong> related to the specified ID or, if Id is not present, an <strong>error message</strong>.
     * @throws MovieException if there is not even a movie with the entered ID
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<Movie> getMovie(int id) throws MovieException, RepositoryException {
        Movie movie = repository.getMovie(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies which matches the searched title.
     * @param title     The title to be searched
     * @return Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies
     * @throws MovieException if there is not even a movie with the entered title
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<Collection<Movie>> getMovieByTitle(String title) throws MovieException, RepositoryException {
        List<Movie> result = repository.getMovieByTitle(title);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies which matches the searched director.
     * @param director     The director to be searched
     * @return Returns a <code>Response Entity</code> with an <code>HttpStatus</code> and a <code>Collection</code> of movies
     * @throws MovieException if there is not even a movie with the entered director
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<Collection<Movie>> getMovieByDirector(String director) throws MovieException, RepositoryException {
        List<Movie> result = repository.getMovieByDirector(director);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Deletes a movie from the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the Id of the movie to be deleted is present, the HttpStatus is <code>200</code> and the response body contains a confirmation message.
     * </p>
     * @param id        The ID whose associated movie is to be removed
     * @return Response Entity with <strong>confirmation message</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws MovieException if there is not even a movie with the entered ID
     * @throws RepositoryException if the repository is empty
     */
    public ResponseEntity<String> deleteMovie(int id) throws MovieException, RepositoryException {
        repository.removeMovie(id);
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

    /**
     * Updates a movie in the repository and returns a <code>Response Entity</code> with an <code>HttpStatus</code>.
     * <p>
     * If the Id of the movie to be updated is present, the HttpStatus is <code>200</code> and the response body contains a confirmation message.
     * </p>
     * @param id        The ID whose associated movie is to be removed
     * @param movie     The Movie with the fields to be update
     * @param patch     A flag to check if is a partial, <code>(patch=true)</code>, or total, <code>(patch=false)</code>, update
     * @return A Response Entity with <strong>confirmation message</strong> or, if list is empty, an <strong>error message</strong>.
     * @throws MovieException if there is not even a movie with the entered ID
     * @throws RepositoryException if the repository is empty
     * @throws FieldsException if the title or director fields are null or empty or blank
     */
    public ResponseEntity<String> updateMovie(int id, Movie movie, boolean patch) throws MovieException, FieldsException, RepositoryException {
        if(!patch){
            repository.checkFields(movie);
        }
        repository.updateMovie(id, movie);
        return new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK);
    }

}
