package me.jun.guestbook.domain.repository;

import me.jun.guestbook.domain.PostCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static me.jun.guestbook.PostCountFixture.POST_COUNT_ID;
import static me.jun.guestbook.PostCountFixture.postCount;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostCountRepositoryTest {

    @Autowired
    private PostCountRepository postCountRepository;

    @Test
    void findByPostIdTest() {
        PostCount expected = postCountRepository.save(postCount());

        PostCount postCount = postCountRepository.findByPostId(POST_COUNT_ID).get();

        assertThat(postCount).isEqualToComparingFieldByField(expected);
    }
}