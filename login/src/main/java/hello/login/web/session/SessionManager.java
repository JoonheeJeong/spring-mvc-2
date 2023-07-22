package hello.login.web.session;

import hello.login.web.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "sessionId";

    private final Map<String, Member> memberMap = new ConcurrentHashMap<>();

    public void createSession(HttpServletResponse response, Member member) {
        String sessionId = UUID.randomUUID().toString();
        memberMap.put(sessionId, member);
        Cookie sessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        sessionCookie.setMaxAge(60 * 30);
        response.addCookie(sessionCookie);
    }

    public Member getSession(HttpServletRequest request) {
        Cookie sessionCookie = findSessionCookie(request);
        if (sessionCookie == null) {
            return null;
        }
        String sessionId = sessionCookie.getValue();
        if (sessionId == null) {
            return null;
        }
        return memberMap.get(sessionId);
    }

    private static Cookie findSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                .findFirst()
                .orElse(null);
    }

    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        Cookie sessionCookie = findSessionCookie(request);
        if (sessionCookie == null) {
            return;
        }
        memberMap.remove(sessionCookie.getValue());
        sessionCookie.setValue(null);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
    }
}
