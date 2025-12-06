package com.hilmsf.cms.controller;

import com.hilmsf.cms.model.dto.request.ProfileRequest;
import com.hilmsf.cms.model.dto.response.BaseResponse;
import com.hilmsf.cms.model.entity.Profile;
import com.hilmsf.cms.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getProfile() {
      try{
          Profile res = profileService.getProfile();
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
    public ResponseEntity<BaseResponse<?>> createProfile(@RequestBody ProfileRequest request) {
        try {
            Profile profile = profileService.createProfile(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(profile));
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
