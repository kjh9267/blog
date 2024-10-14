package me.jun.core.guestbook.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "value")
public class Hits {

    @Column(name = "hits", nullable = false)
    private long value;

    public Hits update() {
        return new Hits(value + 1L);
    }
}
