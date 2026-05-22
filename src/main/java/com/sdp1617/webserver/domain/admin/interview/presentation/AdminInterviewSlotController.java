package com.sdp1617.webserver.domain.admin.interview.presentation;

import com.sdp1617.webserver.domain.admin.interview.application.AdminInterviewSlotService;
import com.sdp1617.webserver.domain.admin.interview.application.dto.response.InterviewSlotResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    public SuccessResponse<List<InterviewSlotResponse>> getInterviewSlots(
            @PathVariable @Positive Long recruitmentId
    ) {
        return SuccessResponse.ok(adminInterviewSlotService.getInterviewSlots(recruitmentId));
    }
}
