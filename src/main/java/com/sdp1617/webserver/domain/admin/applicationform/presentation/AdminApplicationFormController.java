package com.sdp1617.webserver.domain.admin.applicationform.presentation;

import com.sdp1617.webserver.domain.admin.applicationform.application.ApplicationFormService;
import com.sdp1617.webserver.domain.admin.applicationform.application.dto.response.ApplicationFormDetailResponse;
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

@Tag(name = "Admin Application", description = "Admin application detail API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/admin/applications")
public class AdminApplicationFormController {

    private final ApplicationFormService applicationFormService;

    @GetMapping("/{applicationId}")
    @Operation(summary = "Get application detail")
    public SuccessResponse<ApplicationFormDetailResponse> getApplicationForm(@PathVariable @Positive Long applicationId) {
        return SuccessResponse.ok(applicationFormService.getApplicationForm(applicationId));
    }
}
