package me.jun.community.domain;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private final Long id;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private final List<Post> posts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private final List<Comment> comments;

    @Column(unique = true)
    private final String name;

    private final String password;
}
