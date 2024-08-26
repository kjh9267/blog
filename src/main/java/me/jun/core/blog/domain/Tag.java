package me.jun.core.blog.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode(of = "id")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Tag updateName(String name) {
        this.name = name;
        return this;
    }

    public static Tag from(String tagName) {
        return Tag.builder()
                .name(tagName)
                .build();
    }
}
