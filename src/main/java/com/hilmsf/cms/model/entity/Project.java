package com.hilmsf.cms.model.entity;

import com.hilmsf.cms.model.enums.ProjectType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column
    private String title;

    @Column
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectType projectType;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "source_code")
    private String sourceCode;

    @Column
    private List<String> features;

    @Column(name = "is_published")
    private Boolean isPublished;

    @Column(name = "title_slug", nullable = false, unique = true)
    private String titleSlug;

    @PrePersist
    @PreUpdate
    private void generateSlug() {
        if (title != null) {
            this.titleSlug = title.toLowerCase()
                    .trim()
                    .replaceAll("[^a-z0-9\\s]", "")
                    .replaceAll("\\s+", "-")
                    .replaceAll("-+", "-")
                    .replaceAll("^-|-$", "");
        }
    }
}
