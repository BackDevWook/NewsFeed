package com.sprta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String introduction;  // 소개글

    // 소개글 작성
    public Profile(User user, String introduction){
        this.user = user;
        this.introduction = introduction;
    }

    // 소개글 수정
    public void updateIntroduction(String introduction){
        this.introduction = introduction;
    }
}


