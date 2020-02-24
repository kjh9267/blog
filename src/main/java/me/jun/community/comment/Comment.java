package me.jun.community.comment;

import me.jun.community.account.Account;
import me.jun.community.post.Post;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Account account;

    private String author;

    private String content;

}
