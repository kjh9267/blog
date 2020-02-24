package me.jun.community.domain.post;

import me.jun.community.domain.Post;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
class PostRepositoryTest {

    @Test
    public void crud() {
        Post post = new Post();

    }
}