package me.jun.core.blog.application;

import me.jun.core.blog.CategoryFixture;
import me.jun.core.blog.application.exception.CategoryNotFoundException;
import me.jun.core.blog.domain.Category;
import me.jun.core.blog.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void exist_createCategoryTest() {
        given(categoryRepository.save(any()))
                .willReturn(CategoryFixture.category());

        Category category = categoryService.createCategoryOrElseGet(CategoryFixture.CATEGORY_NAME);

        assertThat(category)
                .isEqualToComparingFieldByField(CategoryFixture.category());
    }

    @Test
    void notExist_createCategoryTest() {
        given(categoryRepository.findByName(any()))
                .willReturn(Optional.empty());

        given(categoryRepository.save(any()))
                .willReturn(CategoryFixture.category());

        Category category = categoryService.createCategoryOrElseGet(CategoryFixture.CATEGORY_NAME);

        assertThat(category)
                .isEqualToComparingFieldByField(CategoryFixture.category());
    }

    @Test
    void retrieveCategoryIdTest() {
        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(CategoryFixture.category()));

        assertThat(categoryService.retrieveCategoryById(CategoryFixture.CATEGORY_ID))
                .isEqualToComparingFieldByField(CategoryFixture.category());
    }

    @Test
    void noCategory_retrieveCategoryIdFailTest() {
        given(categoryRepository.findById(any()))
                .willThrow(CategoryNotFoundException.class);

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.retrieveCategoryById(CategoryFixture.CATEGORY_ID)
        );
    }

    @Test
    void retrieveCategoryByNameTest() {
        given(categoryRepository.findByName(any()))
                .willReturn(Optional.of(CategoryFixture.category()));

        assertThat(categoryService.retrieveCategoryByName(CategoryFixture.CATEGORY_NAME))
                .isEqualToComparingFieldByField(CategoryFixture.category());
    }

    @Test
    void noCategory_retrieveCategoryByNameFailTest() {
        given(categoryRepository.findByName(any()))
                .willThrow(CategoryNotFoundException.class);

        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.retrieveCategoryByName("system programming")
        );
    }

    @Test
    void retrieveCategoriesTest() {
        given(categoryRepository.findAll())
                .willReturn(CategoryFixture.categoryList());

        assertThat(categoryService.retrieveCategories())
                .isEqualToComparingFieldByField(CategoryFixture.categoryListResponse());
    }
}
