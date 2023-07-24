package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = { "/", "/login", "logout", "/members/add", "/css/*" };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String logId = (String) httpRequest.getAttribute("logId");
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("인증 시작: [{}][{}]", logId, requestURI);

            Object loginMember = getLoginMember(httpRequest);
            if (loginMember != null) {
                log.info("인증 요청: [{}][{}][{}]", logId, requestURI, loginMember);
            } else if (isFilteredUri(requestURI)) {
                log.info("비인증 요청: [{}][{}]", logId, requestURI);
                ((HttpServletResponse) response).sendRedirect("/login?requestURI=" + requestURI);
                return;
            }
            chain.doFilter(request, response);
        } finally {
            log.info("인증 종료 : [{}][{}]", logId, requestURI);
        }
    }

    private static Object getLoginMember(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        return (session != null) ? session.getAttribute(SessionConst.LOGIN_MEMBER) : null;
    }

    private boolean isFilteredUri(String requestURI) {
        return !PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
