package me.jun.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.ArticleCategoryService;
import me.jun.blog.application.CategoryService;
import me.jun.blog.application.dto.CategoryListResponse;
import me.jun.blog.application.dto.PagedArticleResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ArticleCategoryService articleCategoryService;

    private final CategoryService categoryService;

    @GetMapping("/{categoryName}")
    public ResponseEntity<PagedArticleResponse> retrieveCategoryArticles(@PathVariable String categoryName,
                                                                         @RequestParam("page") int page,
                                                                         @RequestParam("size") int size) {
        PagedArticleResponse response = articleCategoryService.queryCategoryArticles(categoryName, PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CategoryListResponse> retrieveCategories() {
        CategoryListResponse response = categoryService.retrieveCategories();

        return ResponseEntity.ok()
                .body(response);
    }
}
