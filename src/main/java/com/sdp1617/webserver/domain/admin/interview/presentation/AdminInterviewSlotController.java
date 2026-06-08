package com.sdp1617.webserver.domain.admin.interview.presentation;

import com.sdp1617.webserver.domain.admin.interview.application.AdminInterviewSlotService;
import com.sdp1617.webserver.domain.admin.interview.application.dto.response.InterviewSlotResponse;
import com.sdp1617.webserver.global.common.response.ErrorResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Admin Interview Slot", description = "Admin interview slot management API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/admin/recruitments")
public class AdminInterviewSlotController {

    private final AdminInterviewSlotService adminInterviewSlotService;

    @GetMapping("/{recruitmentId}/interview-slots")
    @Operation(summary = "면접 슬롯 목록 조회 (슬롯별 지원자 현황 포함)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "모집 공고 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"RECRUITMENT404","message":"해당 모집 공고를 찾을 수 없습니다.","result":null}""")))
    })
    public SuccessResponse<List<InterviewSlotResponse>> getInterviewSlots(
            @PathVariable @Positive Long recruitmentId
    ) {
        return SuccessResponse.ok(adminInterviewSlotService.getInterviewSlots(recruitmentId));
    }
}
