package me.jun.guestbook.comment.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@Getter
@EqualsAndHashCode(of = "id")
public class PostWriter {

    @Column(name = "writerId")
    private Long id;

    public boolean match(Long writerId) {
        return writerId.equals(id);
    }
}
