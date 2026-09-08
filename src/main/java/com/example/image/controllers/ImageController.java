package com.example.image.controllers;

import com.example.image.models.ImageResponse;
import com.example.image.models.ImageUploadRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponse> uploadImage(@RequestBody ImageUploadRequest imageUploadRequest) {
        // Save the uploaded file and data in db and blob stoage

        return ResponseEntity.ok(null);
    }
}
