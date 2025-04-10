package com.sprta.newsfeed.security.customerror;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 서버
    INTERNAL_SERVER_ERROR(500, "E999", "서버 내부 오류입니다."),

    // 유저
    USER_NOT_FOUND(404, "E001", "해당 유저를 찾을 수 없습니다."),

    // 게시물
    POST_NOT_FOUND(404, "E", "해당 게시물을 찾을 수 없습니다."),
    POST_UPDATE_FAILED(400, "E", "게시물 수정에 실패했습니다."),
    POST_DELETE_FAILED(400, "E", "게시물 삭제에 실패했습니다."),
    POST_CREATION_FAILED(400, "E", "게시물 생성에 실패하셨습니다."),

    // 댓글
    COMMENT_NOT_FOUND(404, "E", "해당 댓글을 찾을 수 없습니다."),
    COMMENT_CREATION_FAILED(400, "E", "댓글 생성에 실패했습니다."),
    COMMENT_UPDATE_FAILED(400, "E", "댓글 수정에 실패했습니다."),
    COMMENT_DELETE_FAILED(400, "E", "댓글 삭제에 실패했습니다."),

    // 팔로우
    ALREADY_FOLLOWING(400, "E", "이미 팔로우한 사용자입니다."),
    CANNOT_FOLLOW_SELF(400, "E", "자기 자신은 팔로우할 수 없습니다."),
    FOLLOW_CREATION_FAILED(400, "E", "팔로우에 실패했습니다."),
    FOLLOW_DELETE_FAILED(400, "E", "언팔로우에 실패했습니다."),
    FOLLOW_NOT_FOUND(404, "E", "팔로우 정보를 찾을 수 없습니다."),

    // 프로필
    PROFILE_NOT_FOUND(404, "E", "해당 사용자의 프로필을 찾을 수 없습니다."),
    PROFILE_CREATION_FAILED(400, "E", "프로필 작성에 실패했습니다."),
    PROFILE_UPDATE_FAILED(400, "E", "프로필 수정에 실패했습니다."),

    // 좋아요

    // 인증/인가
    UNAUTHORIZED_USER(401, "E", "로그인 해주세요."),
    MISMATCH_PASSWORD(400, "E", "비밀번호가 일치하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
