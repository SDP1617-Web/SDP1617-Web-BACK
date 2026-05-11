package com.sdp1617.webserver.domain.review.presentation;

import com.sdp1617.webserver.domain.review.application.ReviewService;
import com.sdp1617.webserver.domain.review.application.dto.response.ReviewResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Review", description = "활동 후기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "활동 후기 목록 조회", description = "활동 후기 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    public SuccessResponse<List<ReviewResponse>> getReviews() {
        return SuccessResponse.ok(reviewService.getReviews());
    }
}
