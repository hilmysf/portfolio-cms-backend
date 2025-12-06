package com.hilmsf.cms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hilmsf.cms.model.dto.request.ProfileRequest;
import com.hilmsf.cms.model.dto.request.ProjectRequest;
import com.hilmsf.cms.model.dto.response.BaseResponse;
import com.hilmsf.cms.model.dto.response.ProjectResponse;
import com.hilmsf.cms.model.entity.Profile;
import com.hilmsf.cms.model.entity.Project;
import com.hilmsf.cms.service.ProfileService;
import com.hilmsf.cms.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getProjects() {
        try {
            List<Project> res = projectService.getProject();
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
            ProjectResponse res = projectService.getProjectById(id);
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
    public ResponseEntity<BaseResponse<?>> createProfile(@RequestBody ProjectRequest request) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            String jsonOutput = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(request);

            // 2. Cetak string JSON tersebut
            System.out.println("JSON Request Body: \n" + jsonOutput);
            Project project = projectService.createProject(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(project));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(BaseResponse.error(400, e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(BaseResponse.error(409, e.getMessage()));
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.error(500, "Unexpected server error"));
        }
    }
}
