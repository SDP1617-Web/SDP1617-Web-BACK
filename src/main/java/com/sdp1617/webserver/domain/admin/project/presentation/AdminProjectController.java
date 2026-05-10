package com.sdp1617.webserver.domain.admin.project.presentation;

import com.sdp1617.webserver.domain.admin.project.application.ProjectService;
import com.sdp1617.webserver.domain.admin.project.application.dto.request.ProjectCreateRequest;
import com.sdp1617.webserver.domain.admin.project.application.dto.request.ProjectUpdateRequest;
import com.sdp1617.webserver.domain.admin.project.application.dto.response.ProjectResponse;
import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    public SuccessResponse<List<ProjectResponse>> getProjects() {
        return SuccessResponse.ok(projectService.getProjects());
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get project detail")
    public SuccessResponse<ProjectResponse> getProject(@PathVariable @Positive Long projectId) {
        return SuccessResponse.ok(projectService.getProject(projectId));
    }

    @PostMapping
    @Operation(summary = "Create project")
    public SuccessResponse<ProjectResponse> createProject(@RequestBody @Valid ProjectCreateRequest request) {
        return SuccessResponse.ok(projectService.createProject(request));
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "Update project")
    public SuccessResponse<ProjectResponse> updateProject(
            @PathVariable @Positive Long projectId,
            @RequestBody @Valid ProjectUpdateRequest request
    ) {
        return SuccessResponse.ok(projectService.updateProject(projectId, request));
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Delete project")
    public SuccessResponse<Void> deleteProject(@PathVariable @Positive Long projectId) {
        projectService.deleteProject(projectId);
        return SuccessResponse.ok();
    }
}
