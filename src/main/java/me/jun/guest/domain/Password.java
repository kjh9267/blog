package me.jun.guest.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode(of = "value")
public class Password {

    @Column(name = "password")
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
