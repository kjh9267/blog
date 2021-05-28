package me.jun.guestbook.guest.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Password {
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
