package me.jun.guestbook.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(unique = true, length = 20, nullable = false)
    private String email;

    @Column(length = 12, nullable = false)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private final List<Post> posts = new ArrayList<>();
}
