package com.sprta.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SignupResponseDto {
    private final Long id;

    private final String username;

    private final String email;


}

