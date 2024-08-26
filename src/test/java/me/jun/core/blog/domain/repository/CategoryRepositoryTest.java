package me.jun.core.blog.domain.repository;

import me.jun.core.blog.CategoryFixture;
import me.jun.core.blog.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Rollback(value = false)
    void findByNameTest() {
        Category expected = categoryRepository.save(CategoryFixture.category());

        Category category = categoryRepository.findByName(CategoryFixture.CATEGORY_NAME)
                .get();

        assertAll(
                () -> assertThat(category).isInstanceOf(Category.class),
                () -> assertThat(category).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void findAllTest() {
        int expected = 100;

        for (long id = 1L; id <= 100; id++) {
            categoryRepository.save(
                    CategoryFixture.category().toBuilder()
                            .id(id)
                            .name(String.valueOf(id))
                            .build()
            );
        }

        List<Category> categories = categoryRepository.findAll();

        assertThat(categories.size())
                .isEqualTo(expected);
    }
}