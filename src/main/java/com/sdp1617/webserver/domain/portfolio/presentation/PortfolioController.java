package com.sdp1617.webserver.domain.portfolio.presentation;

import com.sdp1617.webserver.domain.portfolio.application.PortfolioService;
import com.sdp1617.webserver.domain.portfolio.application.dto.response.PortfolioUploadResponse;
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
            @ApiResponse(responseCode = "400", description = "허용되지 않는 파일 형식 또는 크기 초과",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "허용되지 않는 파일 형식", value = """
                                            {"isSuccess":false,"code":"PORTFOLIO400","message":"허용되지 않는 파일 형식입니다. (pdf, zip, png, jpg만 허용)","result":null}"""),
                                    @ExampleObject(name = "파일 크기 초과", value = """
                                            {"isSuccess":false,"code":"PORTFOLIO400_SIZE","message":"파일 크기가 초과되었습니다. (최대 10MB)","result":null}""")
                            })),
            @ApiResponse(responseCode = "404", description = "지원서 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"PORTFOLIO404","message":"해당 지원서를 찾을 수 없습니다.","result":null}"""))),
            @ApiResponse(responseCode = "409", description = "이미 포트폴리오가 등록된 지원서",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"PORTFOLIO409","message":"이미 포트폴리오가 등록된 지원서입니다.","result":null}"""))),
            @ApiResponse(responseCode = "500", description = "파일 업로드 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"PORTFOLIO500","message":"파일 업로드에 실패했습니다.","result":null}""")))
    })
    public SuccessResponse<PortfolioUploadResponse> upload(
            @Parameter(description = "지원서 ID", example = "1")
            @PathVariable Long applicationId,
            @RequestPart MultipartFile file
    ) {
        return SuccessResponse.ok(portfolioService.upload(applicationId, file));
    }
}
