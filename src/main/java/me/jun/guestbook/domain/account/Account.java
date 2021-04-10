package me.jun.guestbook.domain.account;

import lombok.*;
import me.jun.guestbook.domain.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Account {

    @Builder
    protected Account(Long id, String name, String email, String password) {
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
    @Column(length = 12, nullable = false)
    private Password password;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void validate(String password) {
        if (!this.password.match(password)) {
            throw new WrongPasswordException();
        }
    }
}
