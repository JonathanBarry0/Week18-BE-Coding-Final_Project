package movie.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import movie.review.entity.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {

}
