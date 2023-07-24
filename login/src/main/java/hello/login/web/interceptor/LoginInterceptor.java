package hello.login.web.interceptor;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String logId = (String) request.getAttribute(LogInterceptor.LOG_ID);
        String requestURI = request.getRequestURI();

        Object loginMember = getLoginMember(request);
        if (loginMember != null) {
            log.info("인증 요청: [{}][{}][{}]", logId, requestURI, loginMember);
            return true;
        }

        log.info("비인증 요청: [{}][{}]", logId, requestURI);
        response.sendRedirect("/login?requestURI=" + requestURI);
        return false;
    }

    private static Object getLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? session.getAttribute(SessionConst.LOGIN_MEMBER) : null;
    }
}
