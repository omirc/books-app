package com.util.sample.library.model.mapper;

import com.util.sample.library.entity.Review;
import com.util.sample.library.model.ReviewModel;

public class ReviewMapper {

    public static Review toEntity(final ReviewModel reviewModel) {
        return Review.builder()
                .id(reviewModel.getId())
                .commentBody(reviewModel.getCommentBody())
                .commentDate(reviewModel.getCommentDate())
                .book(reviewModel.getBook())
                .build();
    }

    public static ReviewModel toModel(final Review review) {
        return ReviewModel.builder()
                .id(review.getId())
                .commentBody(review.getCommentBody())
                .commentDate(review.getCommentDate())
                .book(review.getBook())
                .build();
    }
}