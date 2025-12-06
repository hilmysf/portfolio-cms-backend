package com.hilmsf.cms.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    private String name;
    private String email;
    private String description;
    private String motto;
    private String profileImageUrl;
    private String linkedinUrl;
    private String resumeUrl;
    private String githubUrl;

}
