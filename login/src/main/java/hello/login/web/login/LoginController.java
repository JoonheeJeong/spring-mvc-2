package hello.login.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/login-form";
    }
}
