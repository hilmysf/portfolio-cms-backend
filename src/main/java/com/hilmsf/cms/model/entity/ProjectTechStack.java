package com.hilmsf.cms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_tech_stack")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProjectTechStackId.class)
public class ProjectTechStack {
    @Id
    @Column(name = "project_id")
    private UUID projectId;

    @Id
    @Column(name = "tech_stack_id")
    private UUID techStackId;
}
