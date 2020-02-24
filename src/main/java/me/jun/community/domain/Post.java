package me.jun.community.domain;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private final Long id;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<Comment> comments;

    @ManyToOne
    private final Account account;

    private final String author;

    private final String title;

    private final String content;



}
