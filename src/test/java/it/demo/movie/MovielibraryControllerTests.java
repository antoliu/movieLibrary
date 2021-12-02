package it.demo.movie;

import it.demo.movie.controllers.MovieController;
import it.demo.movie.exceptions.EntityException;
import it.demo.movie.exceptions.FieldsException;
import it.demo.movie.exceptions.RepositoryException;
import it.demo.movie.model.Genre;
import it.demo.movie.model.Movie;
import it.demo.movie.services.MovieLibraryService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovielibraryControllerTests {

	@MockBean
	MovieLibraryService service;

	@Autowired
	MovieController controller;

	private static List<Movie> movieList = new ArrayList<>();
	private static Movie firstMovie;
	private static Movie secondMovie;

	@BeforeClass
	public static void setUp(){
		firstMovie = new Movie("1", "Pulp Fiction", "Quentin Tarantino", Genre.DRAMA, 1994);
		secondMovie = (new Movie("2", "Inception", "Christopher Nolan", Genre.THRILLER, 2010));
		movieList.add(firstMovie);
		movieList.add(secondMovie);
		movieList.add(new Movie("3", "Il buono, il brutto e il cattivo", "Sergio Leone", Genre.WESTERN, 1961));
	}

	@Test
	public void testGetAll() throws RepositoryException {
		Mockito.when(service.getAllMovies()).thenReturn(new ResponseEntity<>(movieList, HttpStatus.OK));
		Assert.assertEquals(controller.getAllMovies().getBody().size(), 3);
	}

	@Test
	public void testGetAllFalse() throws RepositoryException {
		Mockito.when(service.getAllMovies()).thenReturn(new ResponseEntity<>(movieList, HttpStatus.OK));
		Assert.assertNotEquals(controller.getAllMovies().getBody().size(), 2);
	}

	@Test(expected = RepositoryException.class)
	public void testGetAllException() throws RepositoryException {
		Mockito.when(service.getAllMovies()).thenThrow(RepositoryException.class);
		controller.getAllMovies();
	}

	@Test
	public void testGetMovie() throws RepositoryException, EntityException {
		Mockito.when(service.getMovie("1")).thenReturn(new ResponseEntity<Movie>(firstMovie, HttpStatus.OK));
		Assert.assertEquals(controller.getMovie("1").getBody(), firstMovie);
	}

	@Test
	public void testGetMovieFalse() throws RepositoryException, EntityException {
		Mockito.when(service.getMovie("1")).thenReturn(new ResponseEntity<Movie>(firstMovie, HttpStatus.OK));
		Assert.assertNotEquals(controller.getMovie("1").getBody(), secondMovie);
	}

	@Test(expected = RepositoryException.class)
	public void testGetMovieRepositoryException() throws RepositoryException, EntityException {
		Mockito.when(service.getMovie("Pulp Fiction")).thenThrow(RepositoryException.class);
		controller.getMovie("Pulp Fiction");
	}

	@Test(expected = EntityException.class)
	public void testGetMovieMovieException() throws RepositoryException, EntityException {
		Mockito.when(service.getMovie("Salvate il soldato Ryan")).thenThrow(EntityException.class);
		controller.getMovie("Salvate il soldato Ryan");
	}

	@Test
	public void testGetByTitle() throws FieldsException, RepositoryException, EntityException {
		List<Movie> result = new ArrayList<>();
		result.add(firstMovie);
		Mockito.when(service.getMovieByTitle("Pulp Fiction")).thenReturn(new ResponseEntity<>(result, HttpStatus.OK));
		Assert.assertEquals(controller.getMovieByTitle("Pulp Fiction").getBody(), result);
	}

	@Test
	public void testGetByTitleFalse() throws FieldsException, RepositoryException, EntityException {
		List<Movie> result = new ArrayList<>();
		result.add(firstMovie);
		Mockito.when(service.getMovieByTitle("Pulp Fiction")).thenReturn(new ResponseEntity<>(result, HttpStatus.OK));
		Assert.assertNotEquals(controller.getMovieByTitle("Pulp Fiction").getBody(), new ArrayList<>());
	}

	@Test(expected = FieldsException.class)
	public void testGetByTitleFieldsException() throws FieldsException, RepositoryException, EntityException {
		Mockito.when(service.getMovieByTitle(" ")).thenThrow(FieldsException.class);
		controller.getMovieByTitle(" ");
	}

	@Test(expected = RepositoryException.class)
	public void testGetByTitleRepositoryException() throws FieldsException, RepositoryException, EntityException {
		Mockito.when(service.getMovieByTitle("Pulp Fiction")).thenThrow(RepositoryException.class);
		controller.getMovieByTitle("Pulp Fiction");
	}

	@Test(expected = EntityException.class)
	public void testGetByTitleMovieException() throws FieldsException, RepositoryException, EntityException {
		Mockito.when(service.getMovieByTitle("Salvate il soldato Ryan")).thenThrow(EntityException.class);
		controller.getMovieByTitle("Salvate il soldato Ryan");
	}

	@Test
	public void testGetByDirector() throws FieldsException, RepositoryException, EntityException {
		List<Movie> result = new ArrayList<>();
		result.add(firstMovie);
		Mockito.when(service.getMovieByDirector("Quentin Tarantino")).thenReturn(new ResponseEntity<>(result, HttpStatus.OK));
		Assert.assertEquals(controller.getMovieByDirector("Quentin Tarantino").getBody(), result);
	}

	@Test
	public void testGetByDirectorFalse() throws FieldsException, RepositoryException, EntityException {
		List<Movie> result = new ArrayList<>();
		result.add(firstMovie);
		List<Movie> otherList = new ArrayList<>();
		Mockito.when(service.getMovieByDirector("Quentin Tarantino")).thenReturn(new ResponseEntity<>(result, HttpStatus.OK));
		Assert.assertNotEquals(controller.getMovieByDirector("Quentin Tarantino").getBody(), otherList);
	}

//	@Test
//	public void testGetByYear() throws FieldsException, RepositoryException, EntityException {
//		List<Movie> result = new ArrayList<>();
//		result.add(firstMovie);
//		Mockito.when(service.getMovieByYear(1994)).thenReturn(new ResponseEntity<>(result, HttpStatus.OK));
//		Assert.assertEquals(controller.getMovieByYear(1994).getBody(), result);
//	}
//
//	@Test
//	public void testGetByYearFalse() throws FieldsException, RepositoryException, EntityException {
//		List<Movie> result = new ArrayList<>();
//		result.add(firstMovie);
//		List<Movie> otherList = new ArrayList<>();
//		Mockito.when(service.getMovieByYear(1994)).thenReturn(new ResponseEntity<>(result, HttpStatus.OK));
//		Assert.assertNotEquals(controller.getMovieByYear(1994).getBody(), otherList);
//	}

	@Test
	public void testAddMovie(){
		ResponseEntity<String> response = new ResponseEntity<>("CONFIRM: Operation done", HttpStatus.OK);
		Mockito.when(service.addMovie(firstMovie)).thenReturn(response);
		Assert.assertEquals(controller.addMovie(firstMovie), response);
	}

	@Test
	public void testAddMovieFalse(){
		ResponseEntity<String> response = new ResponseEntity<>("CONFIRM: Operation done", HttpStatus.OK);
		Mockito.when(service.addMovie(firstMovie)).thenReturn(new ResponseEntity<>("ERROR: Fail operation", HttpStatus.BAD_REQUEST));
		Assert.assertNotEquals(controller.addMovie(firstMovie), response);
	}

	@Test
	public void testAddMovies() throws FieldsException {
		ResponseEntity<String> response = new ResponseEntity<>("CONFIRM: Operation done", HttpStatus.OK);
		Mockito.when(service.addMovies(movieList)).thenReturn(response);
		Assert.assertEquals(controller.addMovies(movieList), response);
	}

	@Test
	public void testAddMoviesFalse() throws FieldsException {
		ResponseEntity<String> response = new ResponseEntity<>("CONFIRM: Operation done", HttpStatus.OK);
		Mockito.when(service.addMovies(movieList)).thenReturn(new ResponseEntity<>("ERROR: Fail operation", HttpStatus.BAD_REQUEST));
		Assert.assertNotEquals(controller.addMovies(movieList), response);
	}

	@Test
	public void testDelete() throws FieldsException {
		ResponseEntity<String> response = new ResponseEntity<>("CONFIRM: Operation done", HttpStatus.OK);
		Mockito.when(service.addMovies(movieList)).thenReturn(response);
		Assert.assertEquals(controller.addMovies(movieList), response);
	}

	@Test
	public void testDeleteFalse() throws FieldsException {
		ResponseEntity<String> response = new ResponseEntity<>("CONFIRM: Operation done", HttpStatus.OK);
		Mockito.when(service.addMovies(movieList)).thenReturn(new ResponseEntity<>("ERROR: Fail operation", HttpStatus.BAD_REQUEST));
		Assert.assertNotEquals(controller.addMovies(movieList), response);
	}

}
