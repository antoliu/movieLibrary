package it.demo.movie.repository;

import it.demo.movie.exceptions.FieldsException;
import it.demo.movie.exceptions.MovieException;
import it.demo.movie.exceptions.RepositoryException;
import it.demo.movie.model.ErrorType;
import it.demo.movie.model.Movie;
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
//        movie.setId(ID);
        this.repository.put(ID, movie);
    }

    /**
     * Checks if any field in the <code>Movie</code> is <code>null</code> or empty. If yes, throws a <code>FieldException</code>
     * @param movie     The <strong>movie</strong> to be checked
     * @throws FieldsException if the title or the director fields are null or empty or blank.
     */
    public void checkFields(Movie movie) throws FieldsException {
        String title = movie.getTitle();
        String director = movie.getDirector();
        //Check if the fields of the movie are null, blank or empty. If yes throws an Exception
        if(!StringUtils.isNotBlank(title) || !StringUtils.isNotBlank(director)){
            throw new FieldsException(ErrorType.TITLE_OR_DIRECTOR_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns a <code>Collection</code> that contains all the Movies object contained in the repository map.
     * @return Returns a <code>Collection</code> that contains all the Movies object contained in the repository map.
     * @throws RepositoryException if the repository is empty
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
     * @throws MovieException if there is not even a movie with the entered ID
     * @throws RepositoryException if the repository is empty
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
     * @throws MovieException if there is not even a movie with the entered title
     * @throws RepositoryException if the repository is empty
     */
    public List<Movie> getMovieByTitle(String title) throws MovieException, RepositoryException {
        if(repository.isEmpty()){
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        //Filters all movies in repository which contains the entered string - to lowerCase is for ignore upper and lower case letters
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
     * @throws MovieException if there is not even a movie with the entered director
     * @throws RepositoryException if the repository is empty
     */
    public List<Movie> getMovieByDirector(String director) throws MovieException, RepositoryException {
        if(repository.isEmpty()){
            throw new RepositoryException(ErrorType.EMPTY_REPOSITORY, HttpStatus.NOT_FOUND);
        }
        //Filters all movies in repository which contains the entered string - to lowerCase is for ignore upper and lower case letters
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
     * @throws MovieException if there is not even a movie with the entered ID
     * @throws RepositoryException if the repository is empty
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
     * @throws MovieException if there is not even a movie with the entered ID
     * @throws RepositoryException if the repository is empty
     * @throws FieldsException if the title or director fields are null or empty or blank
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
        //Check if newTitle is null, empty or blank. If yes throws an Exception
        if (StringUtils.isBlank(newTitle)){
            throw new FieldsException(ErrorType.TITLE_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
        //Check if newDirector is null, empty or blank. If yes throws an Exception
        if(StringUtils.isBlank(newDirector)) {
            throw new FieldsException(ErrorType.DIRECTOR_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
        oldMovie.setTitle(newTitle);
        oldMovie.setDirector(newDirector);
    }

}
