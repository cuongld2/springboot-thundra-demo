package donald.apiwithspringboot.service;


import donald.apiwithspringboot.model.Movie;
import donald.apiwithspringboot.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    public MovieRepository movieRepository;

    public MovieService(final MovieRepository movieRepository) {this.movieRepository = movieRepository;}

    @Transactional(readOnly = true)
    public List<Movie> getAllMovies(final int count){
        return this.movieRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Movie> getMovie(final int id){return this.movieRepository.findById(id);}
}
