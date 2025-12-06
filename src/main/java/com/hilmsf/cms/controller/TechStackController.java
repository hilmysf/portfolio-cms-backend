package com.hilmsf.cms.controller;

import com.hilmsf.cms.model.dto.request.TechStackRequest;
import com.hilmsf.cms.model.dto.response.BaseResponse;
import com.hilmsf.cms.model.entity.TechStack;
import com.hilmsf.cms.service.TechStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tech-stacks")
@RequiredArgsConstructor
public class TechStackController {
    private final TechStackService techStackService;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getTechStackList() {
        try {
            List<TechStack> res = techStackService.getTechStackList();
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
            TechStack res = techStackService.getTechStackById(id);
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
    public ResponseEntity<BaseResponse<?>> createTechStack(@RequestBody TechStackRequest request) {
        try {
            TechStack res = techStackService.createTechStack(request);
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

