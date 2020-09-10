package me.jun.guestbook.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @ManyToOne
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
        account.getPosts().add(this);
    }
}