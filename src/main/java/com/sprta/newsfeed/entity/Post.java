package com.sprta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(length = 500,nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer countComments;

    @Column(nullable = false)
    private Integer countLikes;


    public Post(User user, String title, String content, Integer countComments, Integer countLikes) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.countComments = countComments;
        this.countLikes = countLikes;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }


}
