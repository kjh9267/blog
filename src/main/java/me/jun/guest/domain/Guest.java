package me.jun.guest.domain;

import lombok.*;
import me.jun.guest.domain.exception.WrongPasswordException;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id")
public class Guest {

    @Builder
    protected Guest(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = new Password(password);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, nullable = false)
    private String name;

    @Column(unique = true, length = 20, nullable = false)
    private String email;

    @Embedded
    private Password password;

    public void validate(String password) {
        if (!this.password.match(password)) {
            throw new WrongPasswordException();
        }
    }
}
