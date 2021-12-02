package it.demo.movie.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.demo.movie.exceptions.EntityException;
import it.demo.movie.exceptions.ErrorResponse;
import it.demo.movie.exceptions.RepositoryException;
import it.demo.movie.exceptions.FieldsException;
import it.demo.movie.model.Cinema;
import it.demo.movie.model.Movie;
import it.demo.movie.services.MovieLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MovieController {

    @Autowired
    private MovieLibraryService service;

    /* ----- MOVIES COLLECTION ----- */

    @GetMapping(value = "/movies", produces = "application/json")
    @ApiOperation(value = "Get Movies", notes = "Get all movies from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Collection.class),
            @ApiResponse(code = 404, message = "Empty repository", response = ErrorResponse.class)
    })
    public ResponseEntity<Collection<Movie>> getAllMovies() throws RepositoryException {
        return service.getAllMovies();
    }

    @GetMapping(value = "/movies/{id}" +
            "{id}", produces = "application/json")
    @ApiOperation(value = "Get Movie", notes = "Get a movie from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Movie.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class)
    })
    public ResponseEntity<Movie> getMovie(@ApiParam(name = "id", value = "ID to be searched", required = true, example = "1") @PathVariable String id) throws EntityException, RepositoryException {
        return service.getMovie(id);
    }

    @GetMapping(value = "/movies/title", produces = "application/json")
    @ApiOperation(value = "Get Movie by Title", notes = "Get movies from repository, searching for a title")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Movie.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field title not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<Collection<Movie>> getMovieByTitle(@ApiParam(value = "Title to be searched", required = true) @RequestParam String title) throws EntityException, RepositoryException, FieldsException {
        return service.getMovieByTitle(title);
    }

    @GetMapping(value = "/movies/director", produces = "application/json")
    @ApiOperation(value = "Get Movie by Director", notes = "Get movies from repository, searching for a Director")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Collection.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field director not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<Collection<Movie>> getMovieByDirector(@ApiParam(value = "Director to be searched", required = true) @RequestParam String director) throws EntityException, RepositoryException, FieldsException {
        return service.getMovieByDirector(director);
    }

    @PostMapping(value = "/movies/movie/")
    @ApiOperation(value = "Add Movie", notes = "Add a movie to repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 400, message = "Field not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> addMovie(@ApiParam(value = "Movie to be inserted", required = true) @RequestBody Movie movie) {
        return service.addMovie(movie);
    }

    @PostMapping("/movies/movies/")
    @ApiOperation(value = "Add Movies", notes = "Add a list of movies to repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 400, message = "Body not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> addMovies(@ApiParam(value = "List of movies to be inserted", required = true) @RequestBody Collection<Movie> movies) throws FieldsException {
        return service.addMovies(movies);
    }

    @DeleteMapping("/movies/{id}")
    @ApiOperation(value = "Delete Movie", notes = "Delete a movie from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class)
    })
    public ResponseEntity<String> deleteMovie(@ApiParam(name = "id", value = "ID to be deleted", required = true, example = "1") @PathVariable String id) throws EntityException {
        return service.deleteMovie(id);
    }

    @PutMapping("/movies/{id}")
    @ApiOperation(value = "Update Movie", notes = "Update a movie from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 404, message = "Movie not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> updateMovie(@ApiParam(name = "id", value = "ID to be updated", required = true, example = "1") @PathVariable String id, @ApiParam(value = "Movie with updated fields", required = true) @RequestBody Movie movie) throws EntityException {
        return service.updateMovie(id, movie);
    }

    /* ---------- CINEMAS COLLECTION ---------- */

    @GetMapping(value = "/cinemas", produces = "application/json")
    @ApiOperation(value = "Get Cinemas", notes = "Get all cinemas from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Collection.class),
            @ApiResponse(code = 404, message = "Empty repository", response = ErrorResponse.class)
    })
    public ResponseEntity<Collection<Cinema>> getAllCinemas() throws RepositoryException {
        return service.getAllCinemas();
    }

    @GetMapping(value = "/cinemas/{id}" +
            "{id}", produces = "application/json")
    @ApiOperation(value = "Get Cinema", notes = "Get a cinema from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Movie.class),
            @ApiResponse(code = 404, message = "Cinema not found / Empty Repository", response = ErrorResponse.class)
    })
    public ResponseEntity<Cinema> getCinema(@ApiParam(name = "id", value = "ID to be searched", required = true, example = "1") @PathVariable String id) throws EntityException, RepositoryException {
        return service.getCinema(id);
    }

    @PostMapping(value = "/cinemas/cinema/")
    @ApiOperation(value = "Add Cinema", notes = "Add a cinema to repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 400, message = "Field not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> addCinema(@ApiParam(value = "Movie to be inserted", required = true) @RequestBody Cinema cinema) {
        return service.addCinema(cinema);
    }

    @DeleteMapping("/cinemas/{id}")
    @ApiOperation(value = "Delete Cinema", notes = "Delete a cinema from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 404, message = "Cinema not found / Empty Repository", response = ErrorResponse.class)
    })
    public ResponseEntity<String> deleteCinema(@ApiParam(name = "id", value = "ID to be deleted", required = true, example = "1") @PathVariable String id) throws EntityException {
        return service.deleteCinema(id);
    }

    @PutMapping("/cinemas/{id}")
    @ApiOperation(value = "Update Cinema", notes = "Update a cinema from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 404, message = "Cinema not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<String> updateCinema(@ApiParam(name = "id", value = "ID to be updated", required = true, example = "1") @PathVariable String id, @ApiParam(value = "Movie with updated fields", required = true) @RequestBody Cinema cinema) throws EntityException {
        return service.updateCinema(id, cinema);
    }

    @GetMapping("/cinema/movie/id")
    @ApiOperation(value = "Get cinema projections by movie ID", notes = "Get all the cinemas that project of the selected movie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = String.class),
            @ApiResponse(code = 404, message = "Cinema not found / Empty Repository", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Field not valid", response = ErrorResponse.class)
    })
    public ResponseEntity<Collection<Cinema>> getCinemaByMovieId(@ApiParam(value = "Title to be searched", required = true) @RequestParam String id) throws EntityException, RepositoryException {
        return service.getCinemaByMovie(id);
    }
}
