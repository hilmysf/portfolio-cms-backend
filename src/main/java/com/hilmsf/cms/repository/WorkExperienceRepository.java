package com.hilmsf.cms.repository;

import com.hilmsf.cms.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkExperienceRepository extends JpaRepository<Project, UUID> {
}
