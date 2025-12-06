package com.hilmsf.cms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36) // Tambah length
    // Tetap gunakan generator yang sudah Anda set (@UuidGenerator atau GenericGenerator)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

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