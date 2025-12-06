package com.hilmsf.cms.service;

import com.hilmsf.cms.model.dto.request.TagRequest;
import com.hilmsf.cms.model.entity.Tag;
import com.hilmsf.cms.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(UUID id) {
        return tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public Tag createTag(TagRequest req) {
        Tag tag = new Tag();
        tag.setTitle(req.getTitle());
        Tag result = tagRepository.saveAndFlush(tag);
        System.out.println("tag:" + result.getId());
        return result;
    }
}
