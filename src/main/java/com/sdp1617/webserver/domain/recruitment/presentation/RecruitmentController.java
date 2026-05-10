package com.sdp1617.webserver.domain.recruitment.presentation;

import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.recruitment.application.RecruitmentService;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.QuestionResponse;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Recruitment", description = "모집 공고 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @GetMapping("/active")
    @Operation(summary = "활성 모집 공고 조회", description = "현재 활성화된 모집 공고를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "활성 모집 공고 없음")
    })
    public SuccessResponse<RecruitmentResponse> getActive() {
        return SuccessResponse.ok(recruitmentService.getActive());
    }

    @GetMapping("/{recruitmentId}/questions")
    @Operation(
            summary = "질문 목록 조회",
            description = "모집 공고의 질문 목록을 조회합니다. 공통 질문과 선택한 부서의 질문을 함께 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "모집 공고 없음")
    })
    public SuccessResponse<List<QuestionResponse>> getQuestions(
            @Parameter(description = "모집 공고 ID", example = "1")
            @PathVariable Long recruitmentId,
            @Parameter(description = "부서 (RESEARCH / DESIGN / TECH)", example = "TECH")
            @RequestParam Department department
    ) {
        return SuccessResponse.ok(recruitmentService.getQuestions(recruitmentId, department));
    }
}
