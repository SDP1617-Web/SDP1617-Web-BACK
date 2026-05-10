package com.sdp1617.webserver.domain.admin.project.application;

import com.sdp1617.webserver.domain.admin.project.application.dto.request.ProjectCreateRequest;
import com.sdp1617.webserver.domain.admin.project.application.dto.request.ProjectUpdateRequest;
import com.sdp1617.webserver.domain.admin.project.application.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {

    List<ProjectResponse> getProjects();

    ProjectResponse getProject(Long projectId);

    ProjectResponse createProject(ProjectCreateRequest request);

    ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request);

    void deleteProject(Long projectId);
}
