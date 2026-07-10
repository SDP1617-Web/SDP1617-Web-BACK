package com.sdp1617.webserver.domain.apply.presentation;

import com.sdp1617.webserver.domain.apply.application.ApplyService;
import com.sdp1617.webserver.domain.apply.application.dto.request.ApplySubmitRequest;
import com.sdp1617.webserver.domain.apply.application.dto.response.ApplySubmitResponse;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Recruitment", description = "모집 공고 API")
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
                    - interviewSlotIds: 면접 가능한 시간 슬롯 ID 목록 (필수, 1개 이상), GET /{recruitmentId}/interview-slots 응답 참고
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지원서 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "모집 기간 아님", value = """
                                            {"isSuccess":false,"code":"RECRUITMENT400","message":"현재 지원 가능한 모집 공고가 아닙니다.","result":null}"""),
                                    @ExampleObject(name = "잘못된 문항", value = """
                                            {"isSuccess":false,"code":"APPLICATION400_Q","message":"해당 모집 공고의 문항이 아닙니다.","result":null}"""),
                                    @ExampleObject(name = "필수 문항 누락", value = """
                                            {"isSuccess":false,"code":"APPLICATION400_M","message":"필수 문항에 대한 답변이 누락되었습니다.","result":null}"""),
                                    @ExampleObject(name = "중복 문항 ID", value = """
                                            {"isSuccess":false,"code":"APPLICATION400_D","message":"중복된 문항 ID가 포함되어 있습니다.","result":null}"""),
                                    @ExampleObject(name = "테크팀 세부 직무 미선택", value = """
                                            {"isSuccess":false,"code":"APPLICATION400_T","message":"테크팀 지원 시 세부 직무(프론트엔드/백엔드)를 선택해야 합니다.","result":null}"""),
                                    @ExampleObject(name = "중복 면접 슬롯", value = """
                                            {"isSuccess":false,"code":"APPLICATION400_IS_D","message":"중복된 면접 슬롯 ID가 포함되어 있습니다.","result":null}"""),
                                    @ExampleObject(name = "유효하지 않은 면접 슬롯", value = """
                                            {"isSuccess":false,"code":"APPLICATION400_IS","message":"유효하지 않은 면접 슬롯이 포함되어 있습니다.","result":null}""")
                            })),
            @ApiResponse(responseCode = "404", description = "모집 공고 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"RECRUITMENT404","message":"해당 모집 공고를 찾을 수 없습니다.","result":null}"""))),
            @ApiResponse(responseCode = "409", description = "이미 해당 모집에 지원한 이력 있음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"APPLICATION409","message":"이미 해당 모집에 지원하셨습니다.","result":null}""")))
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
