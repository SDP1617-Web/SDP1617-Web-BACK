package com.sdp1617.webserver.domain.recruitment.presentation;

import com.sdp1617.webserver.domain.apply.entity.Department;
import com.sdp1617.webserver.domain.apply.entity.TechRole;
import com.sdp1617.webserver.domain.recruitment.application.RecruitmentService;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.InterviewSlotSimpleResponse;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.QuestionResponse;
import com.sdp1617.webserver.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.sdp1617.webserver.global.common.response.ErrorResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(responseCode = "404", description = "활성 모집 공고 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"RECRUITMENT404","message":"해당 모집 공고를 찾을 수 없습니다.","result":null}""")))
    })
    public SuccessResponse<RecruitmentResponse> getActive() {
        return SuccessResponse.ok(recruitmentService.getActive());
    }

    @GetMapping("/{recruitmentId}/questions")
    @Operation(
            summary = "지원서 문항 조회",
            description = "모집 공고의 부서별 문항 목록을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "테크팀 세부 직무 미선택",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"RECRUITMENT400_T","message":"테크팀 지원 시 세부 직무(프론트엔드/백엔드)를 선택해야 합니다.","result":null}"""))),
            @ApiResponse(responseCode = "404", description = "모집 공고 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"RECRUITMENT404","message":"해당 모집 공고를 찾을 수 없습니다.","result":null}""")))
    })
    public SuccessResponse<List<QuestionResponse>> getQuestions(
            @Parameter(description = "모집 공고 ID", example = "1")
            @PathVariable Long recruitmentId,
            @Parameter(description = "부서 (RESEARCH / DESIGN / TECH), 반드시 대문자로 입력", example = "TECH")
            @RequestParam Department department,
            @Parameter(description = "테크팀 세부 직무 (FRONTEND / BACKEND), TECH 부서 선택 시 필수, 반드시 대문자로 입력", example = "BACKEND")
            @RequestParam(required = false) TechRole techRole
    ) {
        return SuccessResponse.ok(recruitmentService.getQuestions(recruitmentId, department, techRole));
    }

    @GetMapping("/{recruitmentId}/interview-slots")
    @Operation(
            summary = "면접 가능 시간 슬롯 조회",
            description = "모집 공고의 면접 가능 시간 슬롯 목록을 반환합니다. 부서와 무관하게 공통이며, 지난 시간 슬롯은 제외됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "모집 공고 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"RECRUITMENT404","message":"해당 모집 공고를 찾을 수 없습니다.","result":null}""")))
    })
    public SuccessResponse<List<InterviewSlotSimpleResponse>> getInterviewSlots(
            @Parameter(description = "모집 공고 ID", example = "1")
            @PathVariable Long recruitmentId
    ) {
        return SuccessResponse.ok(recruitmentService.getInterviewSlots(recruitmentId));
    }
}
