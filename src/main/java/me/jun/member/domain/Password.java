package me.jun.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.jun.common.security.PasswordConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode(of = "value")
public class Password {

    @Lob
    @Column(name = "password")
    @Convert(converter = PasswordConverter.class)
    private String value;

    public boolean match(String password) {
        return this.value.equals(password);
    }
}
