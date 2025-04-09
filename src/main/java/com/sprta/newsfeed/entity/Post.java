package com.sprta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer countComments = 0;

    @Column(nullable = false)
    private Integer likesCount = 0;


    public Post(User user, String title, String content, Integer countComments, Integer likesCount) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.countComments = countComments;
        this.likesCount = likesCount;
    }

    public void updateContent(String content) {
        this.content = content;
    }


    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.countComments = 0;
        this.likesCount = 0;
    }



}
