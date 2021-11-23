package me.jun.blog.domain.repository;

import me.jun.blog.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static me.jun.blog.CategoryFixture.CATEGORY_NAME;
import static me.jun.blog.CategoryFixture.category;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByNameTest() {
        Category expected = categoryRepository.save(category());

        Category category = categoryRepository.findByName(CATEGORY_NAME)
                .get();

        assertAll(
                () -> assertThat(category).isInstanceOf(Category.class),
                () -> assertThat(category).isEqualToComparingFieldByField(expected)
        );
    }
}