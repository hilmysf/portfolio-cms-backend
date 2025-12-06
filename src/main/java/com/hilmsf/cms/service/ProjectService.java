package com.hilmsf.cms.service;

import com.hilmsf.cms.model.dto.request.ProjectRequest;
import com.hilmsf.cms.model.dto.response.ProjectResponse;
import com.hilmsf.cms.model.entity.Project;
import com.hilmsf.cms.model.entity.ProjectTechStack;
import com.hilmsf.cms.model.entity.TechStack;
import com.hilmsf.cms.model.enums.ProjectType;
import com.hilmsf.cms.repository.ProjectRepository;
import com.hilmsf.cms.repository.ProjectTechStackRepository;
import com.hilmsf.cms.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TechStackRepository techStackRepository;
    private final ProjectTechStackRepository projectTechStackRepository;


    public List<Project> getProject() {
        return projectRepository.findAll();
    }

    public ProjectResponse getProjectById(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        List<ProjectTechStack> mappings = projectTechStackRepository.findByProjectId(id);

        List<ProjectResponse.TechStackItem> techs = mappings.stream().map(
                m -> { // Fix: Add .orElseThrow() to findById
                    TechStack techStack = techStackRepository.findById(m.getTechStackId()).orElseThrow(() -> new RuntimeException("TechStack not found"));
                    return new ProjectResponse.TechStackItem(
                            techStack.getId(),
                            techStack.getTitle(),
                            techStack.getImageUrl()
                    );
                }
        ).toList();
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getTitleSlug(),
                project.getDescription(),
                project.getProjectType().name(),
                project.getMediaUrl(),
                project.getSourceCode(),
                project.getFeatures(),
                project.getIsPublished(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                techs
        );
    }

    public Project createProject(ProjectRequest request) {
        ProjectType projectType = ProjectType.valueOf(request.getProjectType().trim().toUpperCase());
        Project project = new Project();
        project.setDescription(request.getDescription());
        project.setTitle(request.getTitle());
        project.setTitleSlug(request.getTitleSlug());
        project.setMediaUrl(request.getMediaUrl());
        project.setSourceCode(request.getSourceCode());
        project.setFeatures(request.getFeatures());
        project.setIsPublished(request.getIsPublished());
        project.setProjectType(projectType);
        projectRepository.save(project);
        // Insert tech stack relations
        int index = 0;
        for (UUID techId : request.getTechStackIds()) {
            ProjectTechStack pts = new ProjectTechStack(
                    project.getId(),
                    techId
            );
            projectTechStackRepository.save(pts);
        }
        return project;
    }

}
