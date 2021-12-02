package it.demo.movie;

import it.demo.movie.exceptions.EntityException;
import it.demo.movie.exceptions.RepositoryException;
import it.demo.movie.model.ErrorType;
import it.demo.movie.model.Genre;
import it.demo.movie.model.Movie;
import it.demo.movie.repository.IDatabaseMovieRepository;
import it.demo.movie.services.MovieLibraryService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieLibraryServiceTests {

    @MockBean
    IDatabaseMovieRepository repository;

    @Autowired
    MovieLibraryService service;

    static List<Movie> movieList = new ArrayList<>();
    static Movie firstMovie;
    static Movie secondMovie;
    static Movie thirdMovie;


    @BeforeClass
    public static void setUp(){
        firstMovie = new Movie("1", "Pulp Fiction", "Quentin Tarantino", Genre.DRAMA, 1994);
        secondMovie = (new Movie("2", "Inception", "Christopher Nolan", Genre.THRILLER, 2010));
        thirdMovie = (new Movie("3", "Il buono, il brutto e il cattivo", "Sergio Leone", Genre.WESTERN, 1961));
        movieList.add(firstMovie);
        movieList.add(secondMovie);
        movieList.add(thirdMovie);
    }

    @Test
    public void testGetAll() throws RepositoryException {
        Mockito.when(repository.count()).thenReturn((long) movieList.size());
        Mockito.when(repository.findAll()).thenReturn(movieList);
        Assert.assertEquals(service.getAllMovies().getBody(), movieList);
    }

    @Test
    public void testGetAllFalse() throws RepositoryException {
        List<Movie> list = new ArrayList<>();
        Mockito.when(repository.count()).thenReturn((long) movieList.size());
        Mockito.when(repository.findAll()).thenReturn(movieList);
        Assert.assertNotEquals(service.getAllMovies().getBody(), list);
    }

    @Test
    public void testSecondException(){
        Mockito.when(repository.count()).thenReturn(0L);
        RepositoryException exception = Assert.assertThrows(RepositoryException.class,
                () -> service.getAllMovies());
        Assert.assertEquals(exception.getCode(), ErrorType.EMPTY_REPOSITORY.value());
        Assert.assertEquals(exception.getMessage(), ErrorType.EMPTY_REPOSITORY.getMessage());
        Assert.assertEquals(exception.getStatus(), ErrorType.EMPTY_REPOSITORY.getStatus());
    }

    @Test
    public void testGetMovie() throws RepositoryException, EntityException {
        Mockito.when(repository.count()).thenReturn((long) movieList.size());
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(firstMovie));
        Assert.assertEquals(service.getMovie("1").getBody(), firstMovie);
    }

    @Test
    public void testGetMovieFalse() throws RepositoryException, EntityException {
        Mockito.when(repository.count()).thenReturn((long) movieList.size());
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(firstMovie));
        Assert.assertNotEquals(service.getMovie("1").getBody(), secondMovie);
    }

    @Test
    public void testGetMovieRepositoryException(){
        Mockito.when(repository.count()).thenReturn(0L);
        RepositoryException exception = Assert.assertThrows(RepositoryException.class,
                () -> service.getMovie("4"));
        Assert.assertEquals(exception.getCode(), ErrorType.EMPTY_REPOSITORY.value());
        Assert.assertEquals(exception.getMessage(), ErrorType.EMPTY_REPOSITORY.getMessage());
        Assert.assertEquals(exception.getStatus(), ErrorType.EMPTY_REPOSITORY.getStatus());
    }

    @Test
    public void testGetMovieMovieException(){
        Mockito.when(repository.count()).thenReturn((long) movieList.size());
        Mockito.when(repository.findById("4")).thenReturn(Optional.empty());
        EntityException exception = Assert.assertThrows(EntityException.class,
                () -> service.getMovie("4"));
        Assert.assertEquals(exception.getCode(), ErrorType.MOVIE_NOT_FOUND.value());
        Assert.assertEquals(exception.getMessage(), ErrorType.MOVIE_NOT_FOUND.getMessage());
        Assert.assertEquals(exception.getStatus(), ErrorType.MOVIE_NOT_FOUND.getStatus());
    }

//    @Test
//    public void testGetMovieMovieExceptionFail(){
//        Mockito.when(repository.count()).thenReturn((long) movieList.size());
//        Mockito.when(repository.findById("4")).thenReturn(Optional.empty());
//        EntityException exception = Assert.assertThrows(EntityException.class,
//                () -> service.getMovie("4"));
//        Assert.assertEquals(exception.getCode(), ErrorType.EMPTY_REPOSITORY.value());
//        Assert.assertEquals(exception.getMessage(), ErrorType.EMPTY_REPOSITORY.getMessage());
//    }

//    @Test
//    void testGetByTitle() throws RepositoryException, EntityException, FieldsException {
//        Mockito.when(repository.count()).thenReturn((long) movieList.size());
//        Mockito.when(repository.findByTitle("Pulp Fiction")).thenReturn(firstMovie);
//        Assert.assertEquals(service.getMovieByTitle("Pulp Fiction").getBody(), firstMovie);
//    }
//
//    @Test
//    void testGetByTitleFalse() throws RepositoryException, EntityException, FieldsException {
//        Mockito.when(repository.count()).thenReturn((long) movieList.size());
//        Mockito.when(repository.findByTitle("Pulp Fiction")).thenReturn(firstMovie);
//        Assert.assertNotEquals(service.getMovieByTitle("Pulp Fiction").getBody(), secondMovie);
//    }
//
//    @Test
//    void testGetByDirector() throws RepositoryException, EntityException, FieldsException {
//        List<Movie> result = new ArrayList<>();
//        result.add(firstMovie);
//        Mockito.when(repository.count()).thenReturn((long) movieList.size());
//        Mockito.when(repository.findByDirector("Tarantino")).thenReturn(result);
//        Assert.assertEquals(service.getMovieByDirector("Tarantino").getBody(), result);
//    }
//
//    @Test
//    void testGetByDirectorFalse() throws RepositoryException, EntityException, FieldsException {
//        List<Movie> result = new ArrayList<>();
//        result.add(firstMovie);
//        List<Movie> otherList = new ArrayList<>();
//        Mockito.when(repository.count()).thenReturn((long) movieList.size());
//        Mockito.when(repository.findByDirector("Tarantino")).thenReturn(result);
//        Assert.assertNotEquals(service.getMovieByDirector("Tarantino").getBody(), otherList);
//    }
//
//    @Test
//    void testGetByYear() throws FieldsException, RepositoryException, EntityException {
//        List<Movie> result = new ArrayList<>();
//        result.add(firstMovie);
//        Mockito.when(repository.count()).thenReturn((long) movieList.size());
//        Mockito.when(repository.findByYear(1994)).thenReturn(result);
//        Assert.assertEquals(service.getMovieByYear(1994).getBody(), result);
//    }
//
//    @Test
//    void testGetByYearFalse() throws FieldsException, RepositoryException, EntityException {
//        List<Movie> result = new ArrayList<>();
//        result.add(firstMovie);
//        List<Movie> otherList = new ArrayList<>();
//        Mockito.when(repository.count()).thenReturn((long) movieList.size());
//        Mockito.when(repository.findByYear(1994)).thenReturn(result);
//        Assert.assertNotEquals(service.getMovieByYear(1994).getBody(), otherList);
//    }
//
//    @Test
//    void testAddMovie(){
//        Mockito.when(repository.save(firstMovie)).thenReturn(firstMovie);
//        Assert.assertEquals(service.addMovie(firstMovie), new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK));
//    }
//
//    @Test
//    void testAddMovieFalse(){
//        Mockito.when(repository.save(firstMovie)).thenReturn(firstMovie);
//        Assert.assertNotEquals(service.addMovie(firstMovie), new ResponseEntity<>("ERROR: Fail operation", HttpStatus.BAD_REQUEST));
//    }
//
//    @Test
//    void testAddMovies() throws FieldsException {
//        List<Movie> newList = new ArrayList<>();
//        newList.add(firstMovie);
//        Mockito.when(repository.saveAll(newList)).thenReturn(newList);
//        Assert.assertEquals(service.addMovies(newList), new ResponseEntity<>("CONFIRM: Operation Done", HttpStatus.OK));
//    }
//
//    @Test
//    void testAddMoviesFalse() throws FieldsException {
//        List<Movie> newList = new ArrayList<>();
//        newList.add(firstMovie);
//        Mockito.when(repository.saveAll(newList)).thenReturn(newList);
//        Assert.assertNotEquals(service.addMovies(newList), new ResponseEntity<>("ERROR: Fail operation", HttpStatus.BAD_REQUEST));
//    }
//


}
