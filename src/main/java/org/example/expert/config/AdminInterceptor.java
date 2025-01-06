package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userRole = (String) request.getAttribute("userRole");
        Long userId = (Long) request.getAttribute("userId");
        String requestURI = request.getRequestURI();

        if (!"ADMIN".equals(userRole)) {
            // 권한 없음 로그
            logger.warn("잘못된 접근: userId={}, role={}, URL={}", userId, userRole, requestURI);

            // 403 반환
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 없습니다.");
            return false;
        }

        // 권한 있음 로그
        logger.info("Admin Access: userId={}, role={}, URL={}", userId, userRole, requestURI);

        return true;
    }
}
