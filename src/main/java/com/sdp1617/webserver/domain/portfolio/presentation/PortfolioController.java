package com.sdp1617.webserver.domain.portfolio.presentation;

import com.sdp1617.webserver.domain.portfolio.application.PortfolioService;
import com.sdp1617.webserver.domain.portfolio.application.dto.response.PortfolioUploadResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Portfolio", description = "포트폴리오 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apply/{applicationId}/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "포트폴리오 등록",
            description = """
                    지원서에 포트폴리오 파일을 첨부합니다.

                    - 지원서 1개당 포트폴리오 1개만 등록 가능
                    - 허용 파일 형식: pdf, zip, png, jpg, jpeg
                    - 최대 파일 크기: 10MB
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "포트폴리오 등록 성공"),
            @ApiResponse(responseCode = "400", description = "허용되지 않는 파일 형식 또는 크기 초과"),
            @ApiResponse(responseCode = "404", description = "지원서 없음"),
            @ApiResponse(responseCode = "409", description = "이미 포트폴리오가 등록된 지원서")
    })
    public SuccessResponse<PortfolioUploadResponse> upload(
            @Parameter(description = "지원서 ID", example = "1")
            @PathVariable Long applicationId,
            @RequestPart MultipartFile file
    ) {
        return SuccessResponse.ok(portfolioService.upload(applicationId, file));
    }
}
