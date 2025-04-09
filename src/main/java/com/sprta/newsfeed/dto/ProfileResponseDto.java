package com.sprta.newsfeed.dto;

import com.sprta.newsfeed.entity.Profile;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String introduction;

    public ProfileResponseDto(Profile profile) {
        this.introduction = profile.getIntroduction();
    }
}
