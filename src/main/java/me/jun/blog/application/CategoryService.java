package me.jun.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.exception.CategoryNotFoundException;
import me.jun.blog.domain.Category;
import me.jun.blog.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategoryOrElseGet(String categoryName) {
        final Category category = Category.from(categoryName);

        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> categoryRepository.save(category));
    }

    public Category retrieveCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
