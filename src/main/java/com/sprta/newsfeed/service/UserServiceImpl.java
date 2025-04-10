package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.LoginResponseDto;
import com.sprta.newsfeed.dto.SignupResponseDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository; // 사용자 저장소 (DB 연동)
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화

    // 생성자 주입
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public SignupResponseDto signUp(String userName, String email, String password) {

        // 중복 이메일 체크 (논리 삭제되지 않은 사용자만 검사)
        if (userRepository.existsByEmailAndIsDeletedFalse(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 사용자 엔티티 생성 및 저장
        User user = new User(userName, email, encodedPassword);
        User savedUser = userRepository.save(user);

        // 응답용 DTO 반환
        return new SignupResponseDto(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail());

    }

    @Override
    public LoginResponseDto login(String email, String password) {

        // 이메일로 사용자 조회
         User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }

        // 로그인 성공 → 응답 DTO 생성
        return new LoginResponseDto(user.getId(), user.getEmail());
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
        if (session != null) {
            session.invalidate(); // 세션 무효화 → 로그아웃
        }
    }

    @Override
    @Transactional
    public void signout(Long userId, String inputPassword) {

        // 사용자 ID로 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.")); //수정 필요


        // 이미 탈퇴한 사용자라면 예외 처리
        if (user.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.GONE, "이미 탈퇴한 사용자입니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(inputPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 논리 삭제 처리
        user.markAsDeleted();  // 내부적으로 isDeleted, deletedAt 동시 처리
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"));
    }

}
