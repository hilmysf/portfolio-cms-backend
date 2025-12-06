package com.hilmsf.cms.model.dto.response;

import com.hilmsf.cms.model.entity.TechStack;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String title,
        String titleSlug,
        String description,
        String projectType,
        String mediaPreview,
        String sourceCode,
        List<String> features,
        boolean isPublished,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<TechStackItem> techStacks
) {
    public record TechStackItem(
            UUID id,
            String title,
            String imageUrl
    ){

    }

}
