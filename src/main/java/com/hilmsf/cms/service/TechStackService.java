package com.hilmsf.cms.service;

import com.hilmsf.cms.model.dto.request.TechStackRequest;
import com.hilmsf.cms.model.entity.TechStack;
import com.hilmsf.cms.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TechStackService {
    private final TechStackRepository techStackRepository;

    public TechStack getTechStackById(UUID id) {
        return techStackRepository.findById(id).orElseThrow(() -> new RuntimeException("Tech stack not found"));
    }

    public List<TechStack> getTechStackList(){
        return techStackRepository.findAll();
    }

    public TechStack createTechStack(TechStackRequest req){
        TechStack techStack = new TechStack();
        techStack.setTitle(req.getTitle());
        techStack.setImageUrl(req.getImageUrl());
        return techStackRepository.save(techStack);
    }
}
