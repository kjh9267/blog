package me.jun.guestbook.post.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "value")
public class Hits {

    @Column(name = "hits")
    private long value;

    public Hits update() {
        return new Hits(value + 1L);
    }
}
