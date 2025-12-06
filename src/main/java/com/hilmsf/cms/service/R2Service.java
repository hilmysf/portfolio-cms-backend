package com.hilmsf.cms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class R2Service {
    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket-name}")
    private String bucketName;
    @Value("${cloudflare.r2.public-url}")
    private String publicUrl;
    public R2Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public Map<String, String> uploadFile(MultipartFile file) {
        try {
            String key = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            Map<String, String> result = new HashMap<>();
            result.put("key", key);
            result.put("url", publicUrl + "/" + key);
            result.put("contentType", file.getContentType());
            result.put("size", String.valueOf(file.getSize()));
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    // Get public CDN URL dari key
    public String getPublicUrl(String key) {
        return publicUrl + "/" + key;
    }

    public byte[] downloadFile(String key){
        try{
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            return s3Client.getObject(getObjectRequest).readAllBytes();
        }catch (Exception e){
            throw new RuntimeException("Failed to download file", e);
        }
    }
    // Delete file
    public void deleteFile(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    // List files
    // List files dengan CDN URL
    public List<Map<String, String>> listFiles() {
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);

            return listResponse.contents().stream()
                    .map(s3Object -> {
                        Map<String, String> fileInfo = new HashMap<>();
                        fileInfo.put("key", s3Object.key());
                        fileInfo.put("url", publicUrl + "/" + s3Object.key());
                        fileInfo.put("size", String.valueOf(s3Object.size()));
                        fileInfo.put("lastModified", s3Object.lastModified().toString());
                        return fileInfo;
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to list files: " + e.getMessage(), e);
        }
    }
}
