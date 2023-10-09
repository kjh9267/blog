package me.jun.blog.domain.repository;

import me.jun.blog.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static me.jun.blog.CategoryFixture.CATEGORY_NAME;
import static me.jun.blog.CategoryFixture.category;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Rollback(value = false)
    void findByNameTest() {
        Category expected = categoryRepository.save(category());

        Category category = categoryRepository.findByName(CATEGORY_NAME)
                .get();

        assertAll(
                () -> assertThat(category).isInstanceOf(Category.class),
                () -> assertThat(category).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void findAllTest() {
        for (long id = 1L; id <= 100; id++) {
            categoryRepository.save(
                    category().toBuilder()
                            .id(id)
                            .name(String.valueOf(id))
                            .build()
            );
        }

        List<Category> categories = categoryRepository.findAll();

        assertThat(categories.size())
                .isEqualTo(100);
    }
}