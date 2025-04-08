package com.sprta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User Follower; // 팔로워

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User Following; // 팔로잉

    public Follow(User follower, User following) {
        this.Follower = follower;
        this.Following = following;
    }
}
