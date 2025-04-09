package com.sprta.newsfeed.dto;

import com.sprta.newsfeed.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProfileResponseDto {
    private final String introduction;

    public ProfileResponseDto(Profile profile) {
        this.introduction = profile.getIntroduction();
    }
}
