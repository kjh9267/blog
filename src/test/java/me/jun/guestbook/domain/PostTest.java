package me.jun.guestbook.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
public class PostTest {

    @Test
    public void constructorTest() {
        final Post post = new Post();

        assertThat(post).isInstanceOf(Post.class);
    }
}
