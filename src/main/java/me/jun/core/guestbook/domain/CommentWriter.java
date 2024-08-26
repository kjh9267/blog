package me.jun.core.guestbook.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@Getter
@EqualsAndHashCode(of = "id")
public class CommentWriter {

    @Column(name = "writerEmail")
    private String email;

    public boolean match(String writerEmail) {
        return writerEmail.equals(email);
    }
}
