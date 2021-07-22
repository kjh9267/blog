package me.jun.guestbook.post.domain;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static me.jun.guestbook.post.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void findByIdTest() {
        Post savedPost = postRepository.save(post());

        assertThat(savedPost).isInstanceOf(Post.class)
                .isNotNull()
                .isEqualToComparingFieldByField(post());
    }

    @Test
    void deleteByPostWriterTest() {
        postRepository.save(post());

        postRepository.deleteByPostWriter(postWriter());

        assertThat(postRepository.findById(POST_ID))
                .isEmpty();
    }
}
