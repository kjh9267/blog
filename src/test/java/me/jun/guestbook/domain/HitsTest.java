package me.jun.guestbook.domain;

import org.junit.jupiter.api.Test;

import static me.jun.guestbook.PostCountFixture.hits;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HitsTest {

    @Test
    void constructorTest() {
        assertThat(new Hits()).isInstanceOf(Hits.class);
    }

    @Test
    void constructorTest2() {
        Hits expected = hits();

        assertThat(new Hits(0L))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateTest() {
        Hits hits = hits();

        assertThat(hits.update())
                .isEqualToComparingFieldByField(new Hits(1L));
    }
}