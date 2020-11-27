package com.omirc.library.dto.mapper;

import com.omirc.library.entity.Review;
import com.omirc.library.dto.ReviewDto;

public class ReviewMapper {

    private ReviewMapper() {
    }

    public static Review toEntity(final ReviewDto reviewDto) {
        return Review.builder()
                .id(reviewDto.getId())
                .commentBody(reviewDto.getCommentBody())
                .commentDate(reviewDto.getCommentDate())
                .book(reviewDto.getBook())
                .build();
    }

    public static ReviewDto toModel(final Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .commentBody(review.getCommentBody())
                .commentDate(review.getCommentDate())
                .book(review.getBook())
                .build();
    }
}