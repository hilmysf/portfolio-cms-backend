package com.hilmsf.cms.service;

import com.hilmsf.cms.model.dto.request.ProjectRequest;
import com.hilmsf.cms.model.dto.response.ProjectResponse;
import com.hilmsf.cms.model.entity.Project;
import com.hilmsf.cms.model.entity.TechStack;
import com.hilmsf.cms.model.enums.ProjectType;
import com.hilmsf.cms.repository.ProjectRepository;
import com.hilmsf.cms.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TechStackRepository techStackRepository;


    public List<Project> getProject() {
        return projectRepository.findAll();
    }

    public ProjectResponse getProjectById(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

        List<ProjectResponse.TechStackItem> techs = project.getTechStacks().stream().map(
                techStack -> { // Fix: Add .orElseThrow() to findById
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
        List<TechStack> techStacks = new ArrayList<>();
        if (request.getTechStackIds() != null && !request.getTechStackIds().isEmpty()) {
            techStacks = techStackRepository.findAllById(request.getTechStackIds());
        }
        project.setTechStacks(techStacks);
        projectRepository.save(project);
        return project;
    }

}
