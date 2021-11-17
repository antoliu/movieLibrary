package it.spindox.movielibrary.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.spindox.movielibrary.exceptions.FieldsException;
import it.spindox.movielibrary.exceptions.MovieException;
import it.spindox.movielibrary.exceptions.RepositoryException;
import it.spindox.movielibrary.exceptions.ErrorResponse;
import it.spindox.movielibrary.model.Movie;
import it.spindox.movielibrary.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
public class LibraryController {

    @Autowired
    private LibraryService service;

    @GetMapping("/movies")
    @ApiOperation(value = "Get Movies", notes = "Get all movies from repository")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = Movie.class),
            @ApiResponse(code = 404, message = "Empty repository", response = ErrorResponse.class)
    })
    public ResponseEntity<Collection<Movie>> getAllMovies() throws RepositoryException {
        return service.getAllMovies();
    }

    @PostMapping("/movies/addMovie")
    @ApiOperation(value = "Add Movie", notes = "Add a movie to repository")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 400, message = "Field not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> addMovie(@ApiParam(value = "Movie to be inserted", required = true) @RequestBody Movie movie) throws FieldsException {
        return service.addMovie(movie);
    }

    @PostMapping("/movies/addMovies")
    @ApiOperation(value = "Add Movies", notes = "Add a list of movies to repository")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 400, message = "Body not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> addMovies(@ApiParam(value = "List of movies to be inserted", required = true) @RequestBody Collection<Movie> movies) throws FieldsException {
        return service.addMovies(movies);
    }

    @GetMapping("/movies/{id}")
    @ApiOperation(value = "Get Movie", notes = "Get a movie from repository")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = Movie.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class)
    })
    public ResponseEntity<Movie> getMovie(@ApiParam(value = "ID to be searched", required = true) @PathVariable int id) throws MovieException, RepositoryException {
        return service.getMovie(id);
    }

    @GetMapping("/movies/getByTitle/{title}")
    @ApiOperation(value = "Get Movie by Title", notes = "Get movies from repository, searching for a title")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = Collection.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field title not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<Collection<Movie>> getMovieByTitle(@ApiParam(value = "Title to be searched", required = true) @PathVariable String title) throws MovieException, RepositoryException {
        return service.getMovieByTitle(title);
    }

    @GetMapping("/movies/getByDirector/{director}")
    @ApiOperation(value = "Get Movie by Director", notes = "Get movies from repository, searching for a Director")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = Collection.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field director not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<Collection<Movie>> getMovieByDirector(@ApiParam(value = "Director to be searched", required = true) @PathVariable String director) throws MovieException, RepositoryException {
        return service.getMovieByDirector(director);
    }

    @DeleteMapping("/movies/{id}")
    @ApiOperation(value = "Delete Movie", notes = "Delete a movie from repository")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class)
    })
    public ResponseEntity<String> deleteMovie(@ApiParam(value = "ID to be deleted", required = true) @PathVariable int id) throws MovieException, RepositoryException {
        return service.deleteMovie(id);
    }

    @PutMapping("/movies/{id}")
    @ApiOperation(value = "Update Movie", notes = "Update a movie from repository")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> updateMovie(@ApiParam(value = "ID to be updated", required = true) @PathVariable int id, @ApiParam(value = "Movie with updated fields", required = true) @RequestBody Movie movie) throws FieldsException, MovieException, RepositoryException {
        return service.updateMovie(id, movie, false);
    }

    @PatchMapping("/movies/{id}")
    @ApiOperation(value = "Update Movie", notes = " Paritally update a movie from repository")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> patchUpdateMovie(@ApiParam(value = "ID to be updated", required = true) @PathVariable int id, @ApiParam(value = "Movie with updated fields", required = true) @RequestBody Movie movie) throws MovieException, RepositoryException, FieldsException {
        return service.updateMovie(id, movie, true);
    }


}
