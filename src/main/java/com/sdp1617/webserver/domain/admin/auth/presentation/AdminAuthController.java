package com.sdp1617.webserver.domain.admin.auth.presentation;

import com.sdp1617.webserver.domain.admin.auth.application.dto.request.AdminLoginRequest;
import com.sdp1617.webserver.domain.admin.auth.application.dto.response.AdminLoginResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/login")
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
