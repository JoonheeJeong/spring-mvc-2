package hello.login.web.session;

import hello.login.web.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    private final SessionManager sessionManager = new SessionManager();

    @Test
    void session() {
        // 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(response, member);

        // 요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션 조회
        Member sessionMember = sessionManager.getSession(request);
        assertThat(sessionMember).isSameAs(member);

        // 세션 만료
        sessionManager.expireSession(request, response);
        Member expiredMember = sessionManager.getSession(request);
        assertThat(expiredMember).isNull();
    }

}