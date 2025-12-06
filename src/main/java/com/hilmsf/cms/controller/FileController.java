package com.hilmsf.cms.controller;

import com.hilmsf.cms.model.dto.response.BaseResponse;
import com.hilmsf.cms.service.R2Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private final R2Service r2Service;

    public FileController(R2Service r2Service) {
        this.r2Service = r2Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<Map<String, String>>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> uploadResult = r2Service.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(uploadResult));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<BaseResponse<String>> deleteFile(@PathVariable String key) {
        r2Service.deleteFile(key);
        return ResponseEntity.ok(BaseResponse.success("File deleted successfully"));
    }
}
