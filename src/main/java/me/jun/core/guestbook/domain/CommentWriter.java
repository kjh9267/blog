package me.jun.core.guestbook.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@Getter
@EqualsAndHashCode
public class CommentWriter {

    @Column(name = "writer_id")
    private Long value;

    public boolean match(Long value) {
        return value.equals(this.value);
    }
}
