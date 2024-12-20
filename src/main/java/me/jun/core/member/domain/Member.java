package me.jun.core.member.domain;

import lombok.*;
import me.jun.core.member.domain.exception.WrongPasswordException;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, nullable = false)
    private String name;

    @Column(unique = true, length = 20, nullable = false)
    private String email;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member validate(String password) {
        if (!this.password.match(password)) {
            throw new WrongPasswordException();
        }
        return this;
    }
}
