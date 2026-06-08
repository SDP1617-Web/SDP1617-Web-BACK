package com.sdp1617.webserver.domain.admin.applicationform.presentation;

import com.sdp1617.webserver.domain.admin.applicationform.application.ApplicationFormService;
import com.sdp1617.webserver.domain.admin.applicationform.application.dto.response.ApplicationFormDetailResponse;
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

@Tag(name = "Admin Application", description = "Admin application detail API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/admin/recruitments/{recruitmentId}/applications")
public class AdminApplicationFormController {

    private final ApplicationFormService applicationFormService;

    @GetMapping("/{applicationId}")
    @Operation(summary = "지원서 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "지원서 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"COMMON404","message":"요청한 리소스가 존재하지 않습니다.","result":null}""")))
    })
    public SuccessResponse<ApplicationFormDetailResponse> getApplicationForm(
            @PathVariable @Positive Long recruitmentId,
            @PathVariable @Positive Long applicationId
    ) {
        return SuccessResponse.ok(applicationFormService.getApplicationForm(recruitmentId, applicationId));
    }
}
