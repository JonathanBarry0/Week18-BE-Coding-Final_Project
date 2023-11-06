package movie.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import movie.review.entity.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {

}
