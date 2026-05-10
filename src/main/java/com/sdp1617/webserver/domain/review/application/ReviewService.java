package com.sdp1617.webserver.domain.review.application;

import com.sdp1617.webserver.domain.review.application.dto.response.ReviewResponse;
import com.sdp1617.webserver.domain.review.infrastructure.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewResponse> getReviews() {
        return reviewRepository.findAllByIsDisplayTrueOrderByGenerationDesc().stream()
                .map(ReviewResponse::from)
                .toList();
    }
}
