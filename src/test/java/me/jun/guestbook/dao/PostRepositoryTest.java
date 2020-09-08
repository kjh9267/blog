package me.jun.guestbook.dao;

import me.jun.guestbook.domain.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    private Post post;

    @Before
    public void setUp() {
        final String owner = "jun";
        final String password = "pass";
        final String title = "test title";
        final String content = "test content";

        post = Post.builder()
                .owner(owner)
                .password(password)
                .title(title)
                .content(content)
                .build();
    }

    @Test
    public void findByIdTest() {
        Post savedPost = postRepository.save(post);

        assertThat(savedPost).isInstanceOf(Post.class)
                .isNotNull()
                .isEqualToComparingFieldByField(post);
    }
}
