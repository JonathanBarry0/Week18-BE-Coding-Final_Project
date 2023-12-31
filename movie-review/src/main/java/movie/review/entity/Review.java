package movie.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	
	@EqualsAndHashCode.Exclude
	private String reviewerName;
	@EqualsAndHashCode.Exclude
	private String reviewText;
	@EqualsAndHashCode.Exclude
	private String score;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = false)  // not nullable bc, if we have a review, we must have a movie
	private Movie movie;
}
