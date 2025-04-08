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
    private User follower; // 팔로워

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User following; // 팔로잉

    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
