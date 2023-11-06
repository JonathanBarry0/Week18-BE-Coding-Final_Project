package movie.review.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import movie.review.controller.model.MovieData;
import movie.review.controller.model.MovieData.GenreData;
import movie.review.controller.model.MovieData.ReviewData;
import movie.review.dao.GenreDao;
import movie.review.dao.MovieDao;
import movie.review.dao.ReviewDao;
import movie.review.entity.Genre;
import movie.review.entity.Movie;
import movie.review.entity.Review;

@Service
public class MovieReviewService {

	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private GenreDao genreDao;

	@Transactional(readOnly = false)
	public MovieData saveMovie(MovieData movieData) {
		Long movieId = movieData.getMovieId();		
		Movie movie = findOrCreateMovie(movieId);
		copyMovieFields(movie, movieData);
		Movie dbMovie = movieDao.save(movie);

		return new MovieData(dbMovie);
	}

	private void copyMovieFields(Movie movie, MovieData movieData) {
		movie.setMovieId(movieData.getMovieId());
		movie.setMovieName(movieData.getMovieName());
		movie.setDirector(movieData.getDirector());
		movie.setReleaseDate(movieData.getReleaseDate());
		movie.setRuntime(movieData.getRuntime());
		movie.setBudget(movieData.getBudget());
	}

	private Movie findOrCreateMovie(Long movieId) {
		if(Objects.isNull(movieId)) {
			return new Movie();
		} else {
			return findMovieById(movieId);
		}
	}

	@Transactional(readOnly = false)
	public void deleteMovieById(Long movieId) {
		Movie movie = findMovieById(movieId);
		movieDao.delete(movie);
	}

	private Movie findMovieById(Long movieId) {
		return movieDao.findById(movieId).orElseThrow(
			() -> new NoSuchElementException("Movie with ID=" + movieId + " was not found.")
		);
	}

	@Transactional(readOnly = true)
	public List<MovieData> getAllMovies() {
		return movieDao.findAll()
			.stream()
			.map(MovieData::new)
			.toList();
	}

	@Transactional(readOnly = true)
	public MovieData getMovieById(Long movieId) {
		Movie movie = findMovieById(movieId);
		return new MovieData(movie);
	}

	@Transactional(readOnly = false)
	public ReviewData saveReview(Long movieId, ReviewData reviewData) {
		Movie movie = findMovieById(movieId);
		Long reviewId = reviewData.getReviewId();
		Review review = findOrCreateReview(movieId, reviewId);
		setReviewFields(review, reviewData);
		
		review.setMovie(movie);
		movie.getReviews().add(review);
		
		Review dbReview = reviewDao.save(review);
		return new ReviewData(dbReview);
	}

	private void setReviewFields(Review review, ReviewData reviewData) {
		review.setReviewId(reviewData.getReviewId());
		review.setReviewerName(reviewData.getReviewerName());
		review.setReviewText(reviewData.getReviewText());
		review.setScore(reviewData.getScore());
	}

	private Review findOrCreateReview(Long movieId, Long reviewId) {
		Review review;
		
		if(Objects.isNull(reviewId)) {
			review = new Review();
		}
		else {
			review = findReviewById(movieId, reviewId);
		}
		
		return review;
	}

	private Review findReviewById(Long movieId, Long reviewId) {
		Review review = reviewDao.findById(reviewId).orElseThrow(
			() -> new NoSuchElementException("Review with ID=" + reviewId + " does not exist."));
		if(review.getMovie().getMovieId() != movieId) {
			throw new IllegalArgumentException("Review with ID=" + reviewId + " does not exist for movie with ID=" + movieId + ".");
		}
		return review;
	}

	@Transactional(readOnly = true)
	public List<ReviewData> getReviewsByMovieId(Long movieId) {
		List<Review> reviews = reviewDao.findAll();
		List<ReviewData> response = new LinkedList<ReviewData>();
		
		for(Review review : reviews) {
			if(review.getMovie().getMovieId() == movieId) {
				response.add(new ReviewData(review));
			}
		}
		return response;
	}

	@Transactional(readOnly = false)
	public GenreData saveGenre(Long movieId, GenreData genreData) {
		Movie movie = findMovieById(movieId);
		Long genreId = genreData.getGenreId();
		Genre genre = findOrCreateGenre(genreId, movieId);
		
		copyGenreFields(genre, genreData);
		
		genre.getMovies().add(movie);
		movie.getGenres().add(genre);
		
		Genre dbGenre = genreDao.save(genre);
		return new GenreData(dbGenre);
	}

	private void copyGenreFields(Genre genre, GenreData genreData) {
		genre.setGenreId(genreData.getGenreId());
		genre.setGenreName(genreData.getGenreName());
	}

	private Genre findOrCreateGenre(Long genreId, Long movieId) {
		if(Objects.isNull(genreId)) {
			return new Genre();
		} else {
			return findGenreById(genreId, movieId);
		}
	}

	private Genre findGenreById(Long genreId, Long movieId) {
		Genre genre = genreDao.findById(genreId)
				.orElseThrow(() -> new NoSuchElementException("Genre with ID={}" + genreId + " was not found"));
		for(Movie movie : genre.getMovies()) {
			if(movieId != movie.getMovieId()) {
				throw new IllegalStateException(
						"Genre with ID=" + genreId + " is not owned by movie with ID=" + movieId + ".");
			}
		}
		return genre;
	}

	@Transactional(readOnly = true)
	public List<GenreData> getGenresByMovieId(Long movieId) {
		List<Genre> genres = genreDao.findAll();
		List<GenreData> response = new LinkedList<GenreData>();
		
		for(Genre genre : genres) {
			for(Movie movie : genre.getMovies()) {
				if(movie.getMovieId() == movieId) {
					response.add(new GenreData(genre));
				}
			}
		}
		return response;
	}

	public List<String> getMovieGenreRowsWithMovieId(Long movieId) {
		List<String> rows = new LinkedList<String>();
		
		List<Genre> genres = genreDao.findAll();
		
		for(Genre genre : genres) {
			for(Movie movie : genre.getMovies()) {
				if(movie.getMovieId() == movieId) {
					String movieGenreIds = "Movie with ID=" + movieId + " has genre(s) with ID=" + genre.getGenreId();
					rows.add(movieGenreIds);
				}
			}
		}
		return rows;
	}

}
