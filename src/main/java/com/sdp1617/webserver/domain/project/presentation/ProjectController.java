package com.sdp1617.webserver.domain.project.presentation;

import com.sdp1617.webserver.domain.admin.project.application.ProjectService;
import com.sdp1617.webserver.domain.admin.project.application.dto.response.ProjectResponse;
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
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Project", description = "프로젝트 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "프로젝트 목록 조회", description = "전체 프로젝트 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    public SuccessResponse<List<ProjectResponse>> getProjects() {
        return SuccessResponse.ok(projectService.getProjects());
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "프로젝트 상세 조회", description = "프로젝트 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "프로젝트 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"COMMON404","message":"요청한 리소스가 존재하지 않습니다.","result":null}""")))
    })
    public SuccessResponse<ProjectResponse> getProject(
            @Parameter(description = "프로젝트 ID", example = "1")
            @PathVariable @Positive Long projectId
    ) {
        return SuccessResponse.ok(projectService.getProject(projectId));
    }
}
