package me.jun.blog.domain.repository;

import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Override
    Page<Article> findAll(Pageable pageable);

    Page<Article> findByCategoryId(Long categoryId, Pageable pageable);
}
