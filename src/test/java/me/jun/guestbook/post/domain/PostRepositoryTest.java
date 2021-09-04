package me.jun.guestbook.post.domain;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static me.jun.guestbook.post.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    @Rollback(value = false)
    void deleteByPostWriterTest() {
        postRepository.save(post());
        postRepository.save(post().toBuilder().id(2L).build());
        postRepository.save(post().toBuilder().id(3L).build());

        postRepository.deleteAllByPostWriter(postWriter());

        assertAll(
                () -> assertThat(postRepository.findById(1L)).isEmpty(),
                () -> assertThat(postRepository.findById(2L)).isEmpty(),
                () -> assertThat(postRepository.findById(3L)).isEmpty()
        );
    }
}
