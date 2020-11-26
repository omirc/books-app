package com.util.sample.library.service;

import com.util.sample.library.entity.Book;
import com.util.sample.library.model.ReviewModel;

import java.util.List;
import java.util.Optional;

public interface ReviewService {


    List<ReviewModel> findAllReviews(final Book book);

    Optional<ReviewModel> findReviewById(Long reviewId);

    ReviewModel saveReview(ReviewModel reviewModel);

    void delete(final Long reviewId);
}
