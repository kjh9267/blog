package me.jun.community.account;

import me.jun.community.comment.Comment;
import me.jun.community.post.Post;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    List<Post> posts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    List<Comment> comments;

    @Column(unique = true)
    private String name;

    private String password;
}
