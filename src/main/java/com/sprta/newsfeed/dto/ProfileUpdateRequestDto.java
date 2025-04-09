package com.sprta.newsfeed.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProfileUpdateRequestDto {
    private final String introduction;
    private final String password;
}