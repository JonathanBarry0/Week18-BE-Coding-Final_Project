package movie.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import movie.review.entity.Movie;

public interface MovieDao extends JpaRepository<Movie, Long> {

}
