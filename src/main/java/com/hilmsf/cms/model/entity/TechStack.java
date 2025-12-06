package com.hilmsf.cms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tech_stack")
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "title_slug", length = 100, nullable = false, unique = true)
    private String titleSlug;
    @Column(name = "image_url", length = 255, nullable = false)
    private String imageUrl;

    @PrePersist
    @PreUpdate
    public void generateSlug() {
        if (title != null) {
            this.titleSlug = title.toLowerCase()
                    .trim()
                    .replaceAll("[^a-z0-9\\s]", "")      // Hapus special char KECUALI spasi
                    .replaceAll("\\s+", "-")              // Spasi jadi dash
                    .replaceAll("-+", "-")                // Multiple dash jadi satu
                    .replaceAll("^-|-$", "");             // Hapus dash di awal/akhir
        }
    }
}
