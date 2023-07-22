package hello.login.web;

import hello.login.web.member.Member;
import hello.login.web.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String getHomePage(
            @CookieValue(required = false) Long memberId,
            Model model) {

        if (memberId == null) {
            return "home";
        }

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);

        return "home-login";
    }

//    @GetMapping("/")
    public String getHomePageRawSession(
            HttpServletRequest request,
            Model model) {

        Member loginMember = sessionManager.getSession(request);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);

        return "home-login";
    }

    @GetMapping("/")
    public String getHomePageServletSession(
            HttpServletRequest request,
            Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        // 세션은 다양한 목적으로 이용되므로, 세션은 존재하지만 데이터가 없을 수 있음. 방어 필수!
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "home-login";
    }
}