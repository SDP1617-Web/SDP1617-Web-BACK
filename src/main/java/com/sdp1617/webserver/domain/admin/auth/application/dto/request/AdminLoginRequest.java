package com.sdp1617.webserver.domain.admin.auth.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequest(
        @NotBlank
        @Schema(description="관리자 ID",example="admin")
        String username,
        @NotBlank
        @Schema(description = "관리자 비밀번호",example="admin1234")
        String password
) {
}
