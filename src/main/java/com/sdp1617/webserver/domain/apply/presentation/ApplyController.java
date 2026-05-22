package com.sdp1617.webserver.domain.apply.presentation;

import com.sdp1617.webserver.domain.apply.application.ApplyService;
import com.sdp1617.webserver.domain.apply.application.dto.request.ApplySubmitRequest;
import com.sdp1617.webserver.domain.apply.application.dto.response.ApplySubmitResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Application", description = "지원서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments/{recruitmentId}/apply")
public class ApplyController {

    private final ApplyService applicationService;

    @PostMapping
    @Operation(
            summary = "지원서 등록",
            description = """
                    모집 공고에 지원서를 제출합니다.

                    - 동일한 모집 공고에 중복 지원 불가
                    - 이메일 기준으로 지원자를 식별하며, 최초 지원 시 지원자 정보가 자동 생성됩니다
                    - answers에는 해당 모집의 공통 문항 + 지원 부서 문항 ID를 포함해야 합니다
                    - department: RESEARCH / DESIGN / TECH
                    - interviewSlotIds: 면접 가능한 시간 슬롯 ID 목록 (선택), GET /{recruitmentId}/questions 응답의 interviewSlots 참고
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지원서 등록 성공"),
            @ApiResponse(responseCode = "400", description = "모집 기간이 아니거나 잘못된 문항 포함"),
            @ApiResponse(responseCode = "404", description = "모집 공고 또는 문항 없음"),
            @ApiResponse(responseCode = "409", description = "이미 해당 모집에 지원한 이력 있음")
    })
    public SuccessResponse<ApplySubmitResponse> submit(
            @Parameter(description = "모집 공고 ID", example = "1")
            @PathVariable Long recruitmentId,
            @RequestBody
            @Valid ApplySubmitRequest request
    ) {
        return SuccessResponse.ok(applicationService.submit(recruitmentId, request));
    }
}
