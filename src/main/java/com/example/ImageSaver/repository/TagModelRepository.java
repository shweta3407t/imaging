package com.example.ImageSaver.repository;

import com.example.ImageSaver.models.ListImageResponse;
import com.example.ImageSaver.models.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagModelRepository extends JpaRepository<Tag,Long> {
    @EntityGraph(attributePaths = "tag")
    Tag findByName(String tagName);

    Boolean existsByName(String tag);
}
