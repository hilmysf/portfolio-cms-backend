package com.hilmsf.cms.repository;

import com.hilmsf.cms.model.entity.Project;
import com.hilmsf.cms.model.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TechStackRepository extends JpaRepository<TechStack, UUID> {
}
