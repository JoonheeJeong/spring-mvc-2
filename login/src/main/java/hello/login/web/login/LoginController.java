package hello.login.web.login;

import hello.login.web.SessionConst;
import hello.login.web.member.Member;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String getLoginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/login-form";
    }

//    @PostMapping("/login")
    public String loginWithCookie(
            @Valid @ModelAttribute LoginForm form,
            BindingResult bindingResult,
            HttpServletResponse response) {

        Member member = loginService.login(form.getLoginId(), form.getPassword());
        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "login/login-form";
        }

        Cookie cookie = new Cookie("memberId", Long.toString(member.getId()));
        response.addCookie(cookie);

        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginWithRawSession(
            @Valid @ModelAttribute LoginForm form,
            BindingResult bindingResult,
            HttpServletResponse response) {

        Member member = loginService.login(form.getLoginId(), form.getPassword());
        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "login/login-form";
        }

        sessionManager.createSession(response, member);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginWithServletSession(
            @Valid @ModelAttribute LoginForm form,
            BindingResult bindingResult,
            HttpServletRequest request) {

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디나 비밀번호가 맞지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "login/login-form";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutWithRawSession(HttpServletRequest request, HttpServletResponse response) {
        sessionManager.expireSession(request, response);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutWithServletSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
