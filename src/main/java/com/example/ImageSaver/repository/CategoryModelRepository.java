package com.example.ImageSaver.repository;

import com.example.ImageSaver.models.Category;
import com.example.ImageSaver.models.ListImageResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryModelRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = "category")
    ListImageResponse findByName(String categoryName);

}
