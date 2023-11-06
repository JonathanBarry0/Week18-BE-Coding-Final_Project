DROP TABLE IF EXISTS movie_genre;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
	movie_id int NOT NULL AUTO_INCREMENT,
	movie_name varchar(128) NOT NULL,
	director varchar(128) NOT NULL,
	release_date varchar(64),
	runtime varchar(64),
	budget varchar(64),
	PRIMARY KEY (movie_id)
);

CREATE TABLE review (
	review_id int NOT NULL AUTO_INCREMENT,
	movie_id int NULL,
	reviewer_name varchar(64),
	review_text text,
	score varchar(64) NOT NULL,
	PRIMARY KEY (review_id),
	FOREIGN KEY (movie_id) REFERENCES movie (movie_id) ON DELETE CASCADE
);

CREATE TABLE genre (
	genre_id int NOT NULL AUTO_INCREMENT,
	genre_name varchar(64) NOT NULL,
	PRIMARY KEY (genre_id)
);

CREATE TABLE movie_genre (
	movie_id int NOT NULL,
	genre_id int NOT NULL,
	FOREIGN KEY (movie_id) REFERENCES movie (movie_id) ON DELETE CASCADE,
	FOREIGN KEY (genre_id) REFERENCES genre (genre_id) ON DELETE CASCADE
);
