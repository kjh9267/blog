package me.jun.community.domain;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private final Long id;

    @ManyToOne
    private final Post post;

    @ManyToOne
    private final Account account;

    private final String author;

    private final String content;

}
