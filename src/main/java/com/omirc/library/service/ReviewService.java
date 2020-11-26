package com.omirc.library.service;

import com.omirc.library.entity.Book;
import com.omirc.library.dto.ReviewDto;

import java.util.List;
import java.util.Optional;

public interface ReviewService {


    List<ReviewDto> findAllReviews(final Book book);

    Optional<ReviewDto> findReviewById(Long reviewId);

    ReviewDto saveReview(ReviewDto reviewDto);

    void delete(final Long reviewId);
}
