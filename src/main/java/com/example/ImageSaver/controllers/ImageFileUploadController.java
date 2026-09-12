package com.example.ImageSaver.controllers;


import com.example.ImageSaver.models.*;
import com.example.ImageSaver.service.ImageFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageFileUploadController {

    @Autowired
    public ImageFileUploadService imageFileUploadService;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String > uploadImage(@ModelAttribute ImageUploadRequest imageUploadRequest) throws IOException {
        // Save the uploaded file and data in db and blob stoage

        imageFileUploadService.saveImageFileRequest(imageUploadRequest);

         return ResponseEntity.ok("Image uploaded");


    }



    @GetMapping("/searchByTitle")
    public ResponseEntity<ListImageResponse> searchImageByTitle(@RequestParam String imageTitle){

        ListImageResponse response=  imageFileUploadService.searchImageByTitle(imageTitle);

        return  ResponseEntity.ok(response);

    }

    @GetMapping("/searchByTag")
    public ResponseEntity<ListImageResponse> searchImageByTag(@RequestParam String tag){

        ListImageResponse response=  imageFileUploadService.searchImageByTag(tag);

        return  ResponseEntity.ok(response);

    }
    @GetMapping("/searchByCategory")
    public ResponseEntity<ListImageResponse> searchImageByCategory(@RequestParam String category){

        ListImageResponse response=  imageFileUploadService.searchImageByCategory(category);

        return  ResponseEntity.ok(response);

    }

}
