package me.jun.guestbook.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.guestbook.PostCountFixture.postCount;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostCountTest {

    @Mock
    private Hits hits;

    @Test
    void constructorTest() {
        assertThat(new PostCount())
                .isInstanceOf(PostCount.class);
    }

    @Test
    void constructorTest2() {
        PostCount expected = PostCount.builder()
                .id(1L)
                .hits(new Hits())
                .postId(1L)
                .build();

        assertAll(
                () -> assertThat(postCount()).isInstanceOf(PostCount.class),
                () -> assertThat(postCount()).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void updateHitsTest() {
        PostCount postCount = postCount().toBuilder()
                .hits(hits)
                .build();

        given(hits.update())
                .willReturn(new Hits(1L));

        postCount.updateHits();

        assertThat(postCount.getHits())
                .isEqualToComparingFieldByField(new Hits(1L));
    }
}
