package com.sdp1617.webserver.domain.admin.project.presentation;

import com.sdp1617.webserver.domain.admin.project.application.ProjectService;
import com.sdp1617.webserver.domain.admin.project.application.dto.request.ProjectCreateRequest;
import com.sdp1617.webserver.domain.admin.project.application.dto.request.ProjectUpdateRequest;
import com.sdp1617.webserver.domain.admin.project.application.dto.response.ProjectResponse;
import com.sdp1617.webserver.global.common.response.ErrorResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Admin Project", description = "Admin project CRUD API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/admin/projects")
public class AdminProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "List projects")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    public SuccessResponse<List<ProjectResponse>> getProjects() {
        return SuccessResponse.ok(projectService.getProjects());
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get project detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "프로젝트 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"COMMON404","message":"요청한 리소스가 존재하지 않습니다.","result":null}""")))
    })
    public SuccessResponse<ProjectResponse> getProject(@PathVariable @Positive Long projectId) {
        return SuccessResponse.ok(projectService.getProject(projectId));
    }

    @PostMapping
    @Operation(summary = "Create project")
    @ApiResponse(responseCode = "200", description = "생성 성공")
    public SuccessResponse<ProjectResponse> createProject(@RequestBody @Valid ProjectCreateRequest request) {
        return SuccessResponse.ok(projectService.createProject(request));
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "Update project")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "프로젝트 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"COMMON404","message":"요청한 리소스가 존재하지 않습니다.","result":null}""")))
    })
    public SuccessResponse<ProjectResponse> updateProject(
            @PathVariable @Positive Long projectId,
            @RequestBody @Valid ProjectUpdateRequest request
    ) {
        return SuccessResponse.ok(projectService.updateProject(projectId, request));
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Delete project")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "프로젝트 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {"isSuccess":false,"code":"COMMON404","message":"요청한 리소스가 존재하지 않습니다.","result":null}""")))
    })
    public SuccessResponse<Void> deleteProject(@PathVariable @Positive Long projectId) {
        projectService.deleteProject(projectId);
        return SuccessResponse.ok();
    }
}
