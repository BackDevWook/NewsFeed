package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.*;
import com.sprta.newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        SignupResponseDto signupResponseDto =
                userService.signUp(
                        requestDto.getUserName(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletRequest request) {

        LoginResponseDto login = userService.login(requestDto.getEmail(), requestDto.getPassword());

        HttpSession session = request.getSession(true);

        session.setAttribute(Const.LOGIN_USER, login);

        return ResponseEntity.ok("로그인 되었습니다");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);  // 서비스로 위임
        return ResponseEntity.ok("로그아웃 되었습니다");
    }


    @DeleteMapping("/signout")
    public ResponseEntity<String> signout(@RequestBody DeleteRequestDto requestDto,
                                         HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        LoginResponseDto responseDto = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

        userService.signout(responseDto.getUserId(), requestDto.getPassword());


        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}
