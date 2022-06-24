package me.jun.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.ArticleCategoryService;
import me.jun.blog.application.CategoryService;
import me.jun.blog.application.dto.CategoryListResponse;
import me.jun.blog.application.dto.PagedArticleResponse;
import me.jun.common.hateoas.LinkCreator;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/blog/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ArticleCategoryService articleCategoryService;

    private final CategoryService categoryService;

    private final LinkCreator linkCreator;

    @GetMapping(
            value = "/{categoryName}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PagedArticleResponse> retrieveCategoryArticles(@PathVariable String categoryName,
                                                                         @RequestParam("page") int page,
                                                                         @RequestParam("size") int size) {
        PagedArticleResponse response = articleCategoryService.queryCategoryArticles(categoryName, PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping(
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryListResponse> retrieveCategories() {
        CategoryListResponse response = categoryService.retrieveCategories();

        return ResponseEntity.ok()
                .body(response);
    }
}
