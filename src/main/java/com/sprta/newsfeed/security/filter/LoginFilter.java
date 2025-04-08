package com.sprta.newsfeed.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;


import java.io.IOException;

@Slf4j
@Component
public class LoginFilter implements Filter {

    //로그인 인증이 필요없는 URL 리스트
    private static final String[] WHITE_LIST = {};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 다운 캐스팅
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 요청 URL 가져오기
        String requestURL = request.getRequestURL().toString();

        // 검사가 필요한 URL 이면 실행
        if(!isWhiteList(requestURL)) {

            // 세션을 가져와서 로그인 상태인지 확인
            HttpSession loginSession = request.getSession(false);

            // 로그인 상태가 아니라면 에러 던지기
            if(loginSession == null || loginSession.getAttribute("LOGIN_ID") == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 하셈");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    // 로그인 검사가 필요한 UR1인지 확인
    public boolean isWhiteList(String url) {
        // WHILTE_LIST에 같은 url이 있다면 true, 아니면 false 반환
        return PatternMatchUtils.simpleMatch(WHITE_LIST, url);
    }
}
