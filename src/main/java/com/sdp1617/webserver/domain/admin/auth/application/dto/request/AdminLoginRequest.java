package com.sdp1617.webserver.domain.admin.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
