package com.sdp1617.webserver.domain.admin.auth.presentation;

import com.sdp1617.webserver.domain.admin.auth.application.dto.request.AdminLoginRequest;
import com.sdp1617.webserver.domain.admin.auth.application.dto.response.AdminLoginResponse;
import com.sdp1617.webserver.global.common.response.ErrorResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin Auth", description = "Admin 인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/login")
    @Operation(summary = "Admin 로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 (아이디 또는 비밀번호 불일치)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"COMMON401","message":"인증되지 않은 요청입니다.","result":null}""")))
    })
    public SuccessResponse<AdminLoginResponse> login(
            @RequestBody @Valid AdminLoginRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        SecurityContext securityContext = new SecurityContextImpl(authentication);
        securityContextRepository.saveContext(securityContext, httpServletRequest, httpServletResponse);

        return SuccessResponse.ok(new AdminLoginResponse(
                authentication.getName(),
                "admin login success"
        ));
    }
}
