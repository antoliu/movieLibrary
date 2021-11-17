package it.spindox.movielibrary.repository;

import it.spindox.movielibrary.exceptions.*;
import it.spindox.movielibrary.model.ErrorType;
import it.spindox.movielibrary.model.Movie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {

    /**
     * The repository Maps contains Movie Objects
     */
    private Map<Integer, Movie> repository = new HashMap<>();

    /**
     * Generates the ID for a movie.
     * <p>
     *      This method sets an ID equal to <code>repository.size() + 1</code> and the checks if the keyset alredy contains
     *      the selected ID. If yes, the ID is incremented until it reaches a value not present in keyset.
     * </p>
     * @return An int ID to be used as a key for insertion
     */
    private int generateID(){
        int selectedID = repository.size() + 1;
        while(repository.containsKey(selectedID)){
            selectedID++;
        }
        return selectedID;
    }

    /**
     * Adds a Movie object to the repository map.
     * The corresponding key is obtained via the <code>getId()</code> method of the Movie object.
     * @param movie     The <strong>movie</strong> to be insert
     */
    public void addMovie(Movie movie) {
        int ID = generateID();
        movie.setId(ID);
        this.repository.put(ID, movie);
    }

    /**
     * Checks if any field in the <code>Movie</code> is <code>null</code> or empty. If yes, throws a <code>FieldException</code>
     * @param movie     The <strong>movie</strong> to be checked
     * @throws FieldsException
     */
    public void checkFields(Movie movie) throws FieldsException {
        String title = movie.getTitle();
        String director = movie.getDirector();
        if(!StringUtils.isNotBlank(title) || !StringUtils.isNotBlank(director)){
            throw new FieldsException(ErrorType.TITLE_OR_DIRECTOR_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns a <code>Collection</code> that contains all the Movies object contained in the repository map.
     * @return Returns a <code>Collection</code> that contains all the Movies object contained in the repository map.
     */
    public Collection<Movie> getAllMovies() throws RepositoryException {
        Collection<Movie> resultList = repository.values();
        if(resultList.isEmpty()) throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        return resultList;
    }

    /**
     * Returns the <code>Movie</code> to which the specified Id is mapped, or <code>null</code> if the repository contains no mapping for the key.
     * @param id        The ID whose associated movie is to be returned
     * @return Returns the <strong>movie</strong> to which the specified ID is mapped, or <code>null</code> if the repository contains no mapping for the key.
     * @throws MovieException
     */
    public Movie getMovie(int id) throws MovieException, RepositoryException {
        if(repository.isEmpty()){
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        Movie movie = repository.get(id);
        if(movie == null) throw new MovieException(ErrorType.MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        return movie;
    }

    /**
     * Returns a <code>List<Movie></code> which contains all the movies that match the searched title. If this list is empty throws a <code>MovieException</code>
     * @param title     The title to be searched
     * @return Returns a <code>List<Movie></code> which contains all the movies that match the searched title.
     * @throws MovieException
     */
    public List<Movie> getMovieByTitle(String title) throws MovieException, RepositoryException {
        if(repository.isEmpty()){
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        List<Movie> movieList = repository.values().stream()
                .filter(m -> m.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        if(movieList.isEmpty()){
            throw new MovieException(ErrorType.TITLE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return movieList;
    }

    /**
     * Returns a <code>List<Movie></code> which contains all the movies that match the searched director. If this list is empty throws a <code>MovieException</code>
     * @param director     The director to be searched
     * @return Returns a <code>List<Movie></code> which contains all the movies that match the searched director.
     * @throws MovieException
     */
    public List<Movie> getMovieByDirector(String director) throws MovieException, RepositoryException {
        if(repository.isEmpty()){
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        List<Movie> movieList = repository.values().stream()
                .filter(m -> m.getDirector().toLowerCase().contains(director.toLowerCase()))
                .collect(Collectors.toList());
        if(movieList.isEmpty()){
            throw new MovieException(ErrorType.DIRECTOR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return movieList;
    }

    /**
     * Removes the movie corresponding to the inserted ID
     * @param id        The ID whose associated movie is to be removed
     * @throws MovieException
     */
    public void removeMovie(int id) throws MovieException, RepositoryException {
        if(repository.isEmpty()){
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        if(repository.get(id) == null) throw new MovieException(ErrorType.MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        repository.remove(id);
    }

    /**
     * Updates the movie corresponding to the inserted ID
     * @param id        The ID whose associated movie is to be removed
     * @param movie     The Movie with the fields to be update
     * @throws MovieException
     * @throws RepositoryException
     * @throws FieldsException
     */
    public void updateMovie(int id, Movie movie) throws MovieException, RepositoryException, FieldsException {
        if (repository.isEmpty()) {
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        Movie oldMovie = repository.get(id);
        if (oldMovie == null) {
            throw new MovieException(ErrorType.MOVIE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        String newTitle = movie.getTitle();
        String newDirector = movie.getDirector();
        if (StringUtils.isBlank(newTitle)){
            throw new FieldsException(ErrorType.TITLE_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(newDirector)) {
            throw new FieldsException(ErrorType.DIRECTOR_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
        oldMovie.setTitle(newTitle);
        oldMovie.setDirector(newDirector);
    }

}
