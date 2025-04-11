package com.sprta.newsfeed.security.customerror;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 서버
    INTERNAL_SERVER_ERROR(500, "E999", "서버 내부 오류입니다."),

    // 유저
    USER_NOT_FOUND(404, "E001", "해당 유저를 찾을 수 없습니다."),

    // 게시물
    POST_NOT_FOUND(404, "E", "삭제되거나 존재하지 않는 게시물입니다."),
    POST_UPDATE_FAILED(400, "E", "게시물 수정에 실패했습니다."),
    POST_DELETE_FAILED(400, "E", "게시물 삭제에 실패했습니다."),
    POST_CREATION_FAILED(400, "E", "게시물 생성에 실패하셨습니다."),

    // 댓글
    COMMENT_NOT_FOUND(404, "E", "해당 댓글을 찾을 수 없습니다."),
    COMMENT_CREATION_FAILED(400, "E", "댓글 생성에 실패했습니다."),
    COMMENT_UPDATE_FAILED(400, "E", "댓글 수정에 실패했습니다."),
    COMMENT_DELETE_FAILED(400, "E", "댓글 삭제에 실패했습니다."),
    COMMENT_UPDATE_FORBIDDEN(403, "E", "댓글 작성자만 수정할 수 있습니다."),
    COMMENT_DELETE_FORBIDDEN(403, "E", "게시물 작성자 혹은 댓글 작성자만 삭제할 수 있습니다."),

    // 팔로우
    ALREADY_FOLLOWING(409, "E", "이미 팔로우한 사용자입니다."),
    CANNOT_FOLLOW_SELF(400, "E", "자기 자신은 팔로우할 수 없습니다."),
    FOLLOW_NOT_FOUND(404, "E", "팔로우 정보를 찾을 수 없습니다."),
    NOT_FOLLOWING_USER(409, "E", "해당 사용자를 팔로우하고 있지 않습니다."),

    // 프로필
    PROFILE_NOT_FOUND(404, "E", "해당 사용자의 프로필을 찾을 수 없습니다."),
    PROFILE_CREATION_FAILED(400, "E", "프로필 작성에 실패했습니다."),
    PROFILE_UPDATE_FAILED(400, "E", "프로필 수정에 실패했습니다."),
    PROFILE_ALREADY_EXISTS(400, "E", "이미 프로필이 존재합니다."),

    // 좋아요
    COMMENT_LIKE_NOT_FOUND(404, "E", "해당 댓글에 좋아요 기록이 없습니다."),
    ALREADY_LIKE_COMMENT(409, "E", "이미 해당 댓글에 좋아요를 했습니다."),

    //회원가입 / 회원 탈퇴
    INVALID_INPUT(400, "S001", "입력값이 유효하지 않습니다."),
    DUPLICATED_EMAIL(400, "S002", "이미 존재하는 email입니다."),
    USER_ALREADY_DELETED(410, "S003", "이미 탈퇴한 사용자입니다."),
    PASSWORD_NOT_MATCHED(401, "S004", "비밀번호가 일치하지 않습니다."),
    WITHDRAWN_EMAIL_REUSE_NOT_ALLOWED(409, "S005", "탈퇴한 계정의 이메일은 다시 사용할 수 없습니다."),

    // 게시물 좋아요
    POST_ALREADY_LIKED(400, "E", "이미 좋아요한 게시물입니다."),
    LIKE_NOT_FOUND(404, "E", "좋아요 기록이 존재하지 않습니다."),

    // 인증/인가
    UNAUTHORIZED_USER(401, "E", "로그인 해주세요."),
    MISMATCH_PASSWORD(400, "E", "비밀번호가 일치하지 않습니다."),

    // 요청 오류
    BAD_REQUEST(400, "E000", "잘못된 요청입니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
