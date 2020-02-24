package me.jun.community.post;

import me.jun.community.account.Account;
import me.jun.community.comment.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    private Account account;

    private String author;

    private String title;

    private String content;



}
