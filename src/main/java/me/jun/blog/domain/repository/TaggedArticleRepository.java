package me.jun.blog.domain.repository;

import me.jun.blog.domain.TaggedArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaggedArticleRepository extends JpaRepository<TaggedArticle, Long> {

    Optional<TaggedArticle> findByArticleIdAndTagId(Long articleId, Long tagId);
}
