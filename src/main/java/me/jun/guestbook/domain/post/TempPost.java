package me.jun.guestbook.domain.post;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class TempPost {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

}
