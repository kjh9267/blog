package me.jun.guestbook.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 12, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 12, nullable = false)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private final Set<Post> posts = new HashSet<>();
}
