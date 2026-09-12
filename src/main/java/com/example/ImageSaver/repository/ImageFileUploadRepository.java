package com.example.ImageSaver.repository;

import com.example.ImageSaver.models.ImageUpload;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileUploadRepository extends JpaRepository<ImageUpload, Long> {
    @EntityGraph(attributePaths = "tag")
    ImageUpload findByTitle(String title);
}
