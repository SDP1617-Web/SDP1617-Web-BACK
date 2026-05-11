package com.sdp1617.webserver.domain.review.application.dto.response;

import com.sdp1617.webserver.domain.review.entity.Review;

public record ReviewResponse(
        Long id,
        String name,
        int generation,
        String team,
        String content
) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getName(),
                review.getGeneration(),
                review.getTeam(),
                review.getContent()
        );
    }
}
