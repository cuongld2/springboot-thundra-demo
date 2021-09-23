package donald.apiwithspringboot.controller;


import donald.apiwithspringboot.model.Movie;
import donald.apiwithspringboot.repository.MovieRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Movie", description = "Movie controller")
public class MovieController {

    public MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Operation(summary = "Find all movies", description = "All movies", tags = { "movie" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Movie.class)))) })
    @GetMapping("/movie")
    public List<Movie> index(){
        return movieRepository.findAll();
    }


    @Operation(summary = "Find movie by id", description = "Find movie by id", tags = { "movie" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Movie.class))) })
    @GetMapping("/movie/{id}")
    public Movie show(@PathVariable String id){
        int movieId = Integer.parseInt(id);
        return movieRepository.findById(movieId).orElse(new Movie());
    }

    @PostMapping("/movie/search")
    @Operation(summary = "search movie by text in title", description = "Find movie by text in title", tags = { "movie" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Movie.class)))) })
    public List<Movie> search(@RequestBody Map<String, String> body){
        String searchTerm = body.get("text");
        return movieRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }


    @Operation(summary = "Create a new movie", description = "Create a new movie", tags = { "movie" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Movie.class))) })
    @PostMapping("/movie")
    public Movie create(@RequestBody Map<String, String> body){
        String title = body.get("title");
        String content = body.get("content");
        String author = body.get("author");
        return movieRepository.save(new Movie(title, content, author));
    }


    @Operation(summary = "Update a new movie", description = "Update a new movie", tags = { "movie" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Movie.class))) })
    @PutMapping("/movie/{id}")
    public Movie update(@PathVariable String id, @RequestBody Map<String, String> body){
        int movieId = Integer.parseInt(id);
        // getting movie
        Movie movie = movieRepository.findById(movieId).orElse(new Movie());
        movie.setTitle(body.get("title"));
        movie.setContent(body.get("content"));
        return movieRepository.save(movie);
    }


    @Operation(summary = "Delete a  movie", description = "Delete a movie", tags = { "movie" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation =  Boolean.class))) })
    @DeleteMapping("movie/{id}")
    public boolean delete(@PathVariable String id){
        int movieId = Integer.parseInt(id);
        movieRepository.deleteById(movieId);
        return true;
    }

}
