package me.jun.core.blog.domain.repository;

import me.jun.core.blog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Override
    List<Category> findAll();
}
