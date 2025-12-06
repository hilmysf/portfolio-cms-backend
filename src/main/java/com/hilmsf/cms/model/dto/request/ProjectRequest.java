package com.hilmsf.cms.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProjectRequest {
    private String description;
    private String title;
    private String titleSlug;
    private String projectType;
    private String mediaUrl;
    private String sourceCode;
    private List<String> features;
    private Boolean isPublished;
    public List<UUID> techStackIds;
}
