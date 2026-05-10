package com.sdp1617.webserver.domain.admin.project.application;

import com.sdp1617.webserver.domain.admin.project.application.dto.request.ProjectCreateRequest;
import com.sdp1617.webserver.domain.admin.project.application.dto.request.ProjectUpdateRequest;
import com.sdp1617.webserver.domain.admin.project.application.dto.response.ProjectResponse;
import com.sdp1617.webserver.domain.admin.project.domain.Project;
import com.sdp1617.webserver.domain.admin.project.domain.ProjectRepository;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import com.sdp1617.webserver.global.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<ProjectResponse> getProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectResponse::from)
                .toList();
    }

    @Override
    public ProjectResponse getProject(Long projectId) {
        return ProjectResponse.from(findProject(projectId));
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        Project project = new Project(
                request.name(),
                request.summary(),
                request.description(),
                request.thumbnailUrl(),
                request.githubUrl(),
                request.techStack(),
                request.status(),
                request.startDate(),
                request.endDate()
        );

        return ProjectResponse.from(projectRepository.save(project));
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request) {
        Project project = findProject(projectId);
        project.update(
                request.name(),
                request.summary(),
                request.description(),
                request.thumbnailUrl(),
                request.githubUrl(),
                request.techStack(),
                request.status(),
                request.startDate(),
                request.endDate()
        );
        return ProjectResponse.from(project);
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        Project project = findProject(projectId);
        projectRepository.delete(project);
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, "Project not found: " + projectId));
    }
}
