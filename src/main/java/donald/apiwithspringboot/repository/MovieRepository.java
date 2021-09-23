package donald.apiwithspringboot.repository;

import donald.apiwithspringboot.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

    // custom query to search to movie post by title or content
    List<Movie> findByTitleContainingOrContentContaining(String text, String textAgain);

}
