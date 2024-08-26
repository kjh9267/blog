package me.jun.core.guestbook.domain.repository;


import me.jun.core.guestbook.domain.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static me.jun.core.guestbook.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostRepositoryTest {

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
    void deleteAllByPostWriterTest() {
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

    @Test
    void findAllTest() {
        postRepository.save(post());
        postRepository.save(post().toBuilder().id(2L).build());
        postRepository.save(post().toBuilder().id(3L).build());

        Page<Post> posts = postRepository.findAll(pageRequest());

        assertAll(
                () -> assertThat(posts.getTotalElements()).isEqualTo(3),
                () -> assertThat(posts.getTotalPages()).isEqualTo(2)
        );
    }
}
