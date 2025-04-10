package com.sprta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private Integer countLikes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    public Comment(String content, Integer countLikes, User user, Post post) {
        this.content = content;
        this.countLikes = countLikes;
        this.user = user;
        this.post = post;
    }
}