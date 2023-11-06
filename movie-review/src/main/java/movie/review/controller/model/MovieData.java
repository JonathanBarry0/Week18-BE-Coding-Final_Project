package movie.review.controller.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import movie.review.entity.Genre;
import movie.review.entity.Movie;
import movie.review.entity.Review;

@Data
@NoArgsConstructor
public class MovieData {
	private Long movieId;
	private String movieName;
	private String director;
	private String releaseDate;
	private String runtime;
	private String budget;
	private Set<ReviewData> reviews = new HashSet<>();
	private Set<GenreData> genres = new HashSet<>();

	public MovieData(Movie movie) {  // constructor
		movieId = movie.getMovieId();
		movieName = movie.getMovieName();
		director = movie.getDirector();
		releaseDate = movie.getReleaseDate();
		runtime = movie.getRuntime();
		budget = movie.getBudget();

		for (Review review : movie.getReviews()) {
			reviews.add(new ReviewData(review));
		}

		for (Genre genre : movie.getGenres()) {
			genres.add(new GenreData(genre));
		}
	}

	@Data
	@NoArgsConstructor
	public static class ReviewData {
		private Long reviewId;
		private String reviewerName;
		private String reviewText;
		private String score;

		public ReviewData(Review review) {  // constructor 
			reviewId = review.getReviewId();
			reviewerName = review.getReviewerName();
			reviewText = review.getReviewText();
			score = review.getScore();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class GenreData {
		private Long genreId;
		private String genreName;
		
		public GenreData(Genre genre) {  // constructor
			genreId = genre.getGenreId();
			genreName = genre.getGenreName();
		}
	}

}
