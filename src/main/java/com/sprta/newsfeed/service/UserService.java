package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.LoginResponseDto;
import com.sprta.newsfeed.dto.SignupResponseDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupResponseDto signUp(String username, String email, String password) {

        if (userRepository.existsByEmailAndIsDeletedFalse(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, encodedPassword);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail());

    }

    public LoginResponseDto login(String email, String password) {

         User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }

        return new LoginResponseDto(user.getEmail(), "로그인 성공");
    }

    @Transactional
    public void delete(Long userId, String inputPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (user.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.GONE, "이미 탈퇴한 사용자입니다.");
        }

        if (!passwordEncoder.matches(inputPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
    }

}
