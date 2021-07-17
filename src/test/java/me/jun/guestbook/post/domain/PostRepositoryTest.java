package me.jun.guestbook.post.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static me.jun.guestbook.post.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
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
    void deleteByWriterIdTest() {
        postRepository.save(post());

        postRepository.deleteByWriterId(WRITER_ID);

        assertThat(postRepository.findById(POST_ID))
                .isEmpty();
    }
}
