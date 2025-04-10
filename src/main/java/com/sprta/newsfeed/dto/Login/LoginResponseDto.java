package com.sprta.newsfeed.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LoginResponseDto {

    private final Long userId;

    private final String email;
    // 이외 응답에 필요한 데이터들을 필드로 구성하면 된다.
    // 필요한 생성자

    public Long getUserId() {
        return this.userId;
    }

    public String getEmail() {
        return this.email;
    }

}