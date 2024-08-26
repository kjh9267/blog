package me.jun.core.guestbook.domain.repository;

import me.jun.core.guestbook.domain.PostCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static me.jun.core.guestbook.PostCountFixture.POST_COUNT_ID;
import static me.jun.core.guestbook.PostCountFixture.postCount;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
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