package me.jun.guestbook.domain.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void createRead() {
        // Given
        Post post = Post.builder()
                .title("my post title")
                .author("junho")
                .content("my post content")
                .password("pass")
                .build();

        postRepository.save(post);

        // When
        List<Post> posts = postRepository.findAll();

        // Then
        Post myPost = posts.get(0);
        assertThat(myPost.getTitle()).isEqualTo("my post title");
        assertThat(myPost.getAuthor()).isEqualTo("junho");
        assertThat(myPost.getContent()).isEqualTo("my post content");
        assertThat(myPost.getPassword()).isEqualTo("pass");
    }
}