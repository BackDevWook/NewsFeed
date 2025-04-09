package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    // Optional 처리 후 예외 던지는 default 메서드
    default Long findIdByEmailAndPassword(String email, String password) {
        return findByEmailAndPassword(email, password)
                .map(User::getId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일 또는 비밀번호가 일치하지 않습니다."));
    }

    boolean existsByEmailAndIsDeletedFalse(String email);

    Optional<User> findByEmailAndIsDeletedFalse(String email);



}
