package com.hilmsf.cms.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ProjectTechStackId implements Serializable {
    private UUID projectId;
    private UUID techStackId;

    // Default constructor
    public ProjectTechStackId() {}

    // Constructor
    public ProjectTechStackId(UUID projectId, UUID techStackId) {
        this.projectId = projectId;
        this.techStackId = techStackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectTechStackId that = (ProjectTechStackId) o;
        return Objects.equals(projectId, that.projectId) &&
                Objects.equals(techStackId, that.techStackId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, techStackId);
    }
}
