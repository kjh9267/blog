package me.jun.guestbook;

import me.jun.guestbook.domain.Hits;
import me.jun.guestbook.domain.PostCount;

public abstract class PostCountFixture {

    public static final Long POST_COUNT_ID = 1L;

    public static Hits hits() {
        return new Hits(0L);
    }

    public static PostCount postCount() {
        return PostCount.builder()
                .id(POST_COUNT_ID)
                .hits(hits())
                .build();
    }
}
