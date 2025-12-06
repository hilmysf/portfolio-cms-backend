package com.hilmsf.cms.controller;

import com.hilmsf.cms.model.dto.request.ProjectRequest;
import com.hilmsf.cms.model.dto.request.TagRequest;
import com.hilmsf.cms.model.dto.response.BaseResponse;
import com.hilmsf.cms.model.entity.Project;
import com.hilmsf.cms.model.entity.Tag;
import com.hilmsf.cms.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getTags() {
        try {
            List<Tag> res = tagService.getTags();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(res));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(BaseResponse.error(400, e.getMessage()));

        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(BaseResponse.error(409, e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.error(500, "Unexpected server error"));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> findById(@PathVariable UUID id) {
        try {
            Tag res = tagService.getTagById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(res));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(BaseResponse.error(400, e.getMessage()));

        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(BaseResponse.error(409, e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.error(500, "Unexpected server error"));
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<?>> createTag(@RequestBody TagRequest request) {
        try {
            Tag res = tagService.createTag(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(res));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(BaseResponse.error(400, e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(BaseResponse.error(409, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.error(500, "Unexpected server error"));
        }
    }

}
