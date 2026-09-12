package com.example.ImageSaver.repository;

import com.example.ImageSaver.models.Category;
import com.example.ImageSaver.models.ListImageResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryModelRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
