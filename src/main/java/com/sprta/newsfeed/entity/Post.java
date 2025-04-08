package com.sprta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
@EnableJpaAuditing
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(length = 500,nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer countComments;

    @Column(nullable = false)
    private Integer countLikes;

    public Post(User user, String content, Integer countComments, Integer countLikes) {
        this.user = user;
        this.content = content;
        this.countComments = 0;
        this.countLikes = 0;
    }
}
