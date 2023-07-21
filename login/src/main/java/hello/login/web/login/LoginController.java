package hello.login.web.login;

import hello.login.web.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String getLoginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/login-form";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginForm form,
            BindingResult bindingResult) {

        Member member = loginService.login(form.getLoginId(), form.getPassword());
        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "login/login-form";
        }

        // TODO login 성공 로직

        return "redirect:/";
    }
}
