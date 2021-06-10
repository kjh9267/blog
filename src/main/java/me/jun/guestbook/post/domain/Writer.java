package me.jun.guestbook.post.domain;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
public class Writer {

    @Column
    private Long guestId;

    @Column
    private String name;

    protected Writer() {

    }

    protected Writer(Long guestId, String name) {
        this.guestId = guestId;
        this.name = name;
    }
}
