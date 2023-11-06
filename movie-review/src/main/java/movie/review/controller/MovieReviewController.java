package movie.review.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import movie.review.controller.model.MovieData;
import movie.review.controller.model.MovieData.GenreData;
import movie.review.controller.model.MovieData.ReviewData;
import movie.review.service.MovieReviewService;

@RestController  // this will let Spring know this is our controller class
@RequestMapping("/movie_review")  // tells Spring the REST operations must be one a path that starts with "/movie_review"
@Slf4j  // our logger
public class MovieReviewController {
	@Autowired
	private MovieReviewService mrService;
	
	@PostMapping("/movie")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MovieData createMovie(@RequestBody MovieData movieData) {
		log.info("Creating movie {}", movieData);
		return mrService.saveMovie(movieData);
	}
	
	@PutMapping("/movie/{movieId}")
	public MovieData updateMovieById(@PathVariable Long movieId, @RequestBody MovieData movieData) {
		movieData.setMovieId(movieId);
		log.info("Updating movie with ID={}", movieId);
		return mrService.saveMovie(movieData);
	}
	
	@GetMapping("/movie")
	public List<MovieData> getAllMovies() {
		log.info("Retrieving all movies");
		return mrService.getAllMovies();
	}
	
	@GetMapping("/movie/{movieId}")
	public MovieData getMovieById(@PathVariable Long movieId) {
		log.info("Retrieving movie with ID={}", movieId);
		return mrService.getMovieById(movieId);
	}
	
	@DeleteMapping("/movie")
	public void deleteAllMovies() {
		log.info("Attempting to delete all movies");
		throw new UnsupportedOperationException("Deleting all movies is not allowed.");
	}
	
	@DeleteMapping("/movie/{movieId}")
	public Map<String, String> deleteMovieById(@PathVariable Long movieId) {
		log.info("Deleting movie with ID={}", movieId);
		mrService.deleteMovieById(movieId);
		return Map.of("message", "Deletion of movie with ID=" + movieId + " was successful.");
	}
	
	@PostMapping("/movie/{movieId}/review")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ReviewData createReviewForMovieId(@PathVariable Long movieId, @RequestBody ReviewData reviewData) {
		log.info("Creating review {} for movie with ID={}", reviewData, movieId);
		return mrService.saveReview(movieId, reviewData);
	}
	
	@GetMapping("/movie/{movieId}/review")
	public List<ReviewData> getReviewsByMovieId(@PathVariable Long movieId) {
		log.info("Retrieving all reviews for movie with ID={}", movieId);
		return mrService.getReviewsByMovieId(movieId);
	}
	
	@PostMapping("/movie/{movieId}/genre")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GenreData createGenreForMovieId(@PathVariable Long movieId, @RequestBody GenreData genreData) {
		log.info("Creating genre {} for movie with ID={}", genreData, movieId);
		return mrService.saveGenre(movieId, genreData);
	}
	
	@GetMapping("/movie/{movieId}/genre")
	public List<GenreData> getGenresbyMovieId(@PathVariable Long movieId) {
		log.info("Retrieving all reviews for movie with ID={}", movieId);
		return mrService.getGenresByMovieId(movieId);
	}
	
	@GetMapping("/movie/{movieId}/movie_genre")
	public List<String> getMovieGenreRowsWithMovieId(@PathVariable Long movieId) {
		log.info("Retrieving movie_genre rows with movie ID={}", movieId);
		return mrService.getMovieGenreRowsWithMovieId(movieId);
	}
}
