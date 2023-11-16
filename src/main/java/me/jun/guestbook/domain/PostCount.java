package me.jun.guestbook.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
public class PostCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Hits hits;

    @Column
    private Long postId;

    @Version
    private Long version;

    public PostCount updateHits() {
        this.hits = hits.update();
        return this;
    }
}
