package me.jun.member.domain;

import lombok.EqualsAndHashCode;
import me.jun.common.security.PasswordConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@EqualsAndHashCode(of = "value")
public class Password {

    @Lob
    @Column(name = "password")
    @Convert(converter = PasswordConverter.class)
    private String value;

    protected Password() {
    }

    protected Password(String value) {
        this.value = value;
    }

    public boolean match(String password) {
        return this.value.equals(password);
    }
}
