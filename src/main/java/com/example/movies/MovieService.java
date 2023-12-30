package com.example.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }

//    public void deleteReview(String imdbId, ObjectId reviewId) {
//        movieRepository.findMovieByImdbId(imdbId).ifPresent(movie -> {
//            List<Review> updatedReviews = movie.getReviewIds().stream()
//                    .filter(review -> !review.getId().toString().equals(reviewId))
//                    .collect(Collectors.toList());
//
//            movie.setReviewIds(updatedReviews);
//            movieRepository.save(movie);
//        });
}

