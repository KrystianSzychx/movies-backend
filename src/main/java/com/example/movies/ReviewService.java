package com.example.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review createReview(String reviewBody, String imdbId) {
        Review review = reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }

    public void deleteReviewByReviewId(String reviewId) {
        Optional<Review> optionalReview = reviewRepository.findByReviewId(reviewId);
        optionalReview.ifPresent(reviewRepository::delete);
    }

    public Review updateReview(String reviewId, String reviewBody) {
        Optional<Review> optionalReview = reviewRepository.findByReviewId(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setBody(reviewBody);
            return reviewRepository.save(review);
        } else {
            throw new ReviewNotFoundException("Review not found with ID: " + reviewId);
        }
    }

    public class ReviewNotFoundException extends RuntimeException {
        public ReviewNotFoundException(String message) {
            super(message);
        }
    }
}
