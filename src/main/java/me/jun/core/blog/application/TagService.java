package me.jun.core.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.core.blog.domain.Tag;
import me.jun.core.blog.domain.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag createTagOrElseGet(String tagName) {
        Tag tag = Tag.from(tagName);

        return tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(tag));
    }
}
