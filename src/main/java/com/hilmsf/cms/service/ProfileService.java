package com.hilmsf.cms.service;

import com.hilmsf.cms.model.dto.request.ProfileRequest;
import com.hilmsf.cms.model.entity.Profile;
import com.hilmsf.cms.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Profile getProfile() {
        return profileRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public Profile createProfile(ProfileRequest request) {
        Profile profile = new Profile();
        profile.setName(request.getName());
        profile.setEmail(request.getEmail());
        profile.setDescription(request.getDescription());
        profile.setMotto(request.getMotto());
        profile.setProfileImageUrl(request.getProfileImageUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setResumeUrl(request.getResumeUrl());
        profile.setGithubUrl(request.getGithubUrl());
        return profileRepository.save(profile);
    }
}
