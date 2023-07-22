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

    @GetMapping("/")
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
}