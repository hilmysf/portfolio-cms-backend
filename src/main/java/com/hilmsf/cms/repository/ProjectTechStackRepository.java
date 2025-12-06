package com.hilmsf.cms.repository;

import com.hilmsf.cms.model.entity.ProjectTechStack;
import com.hilmsf.cms.model.entity.ProjectTechStackId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectTechStackRepository extends JpaRepository<ProjectTechStack, ProjectTechStackId> {

    List<ProjectTechStack> findByProjectId(UUID projectId);

    void deleteByProjectId(UUID projectId);
}
