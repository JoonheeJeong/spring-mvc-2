package hello.login.web.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LoginForm {

    @Pattern(regexp = "^[0-9a-zA-Z_]{4,16}$")
    private String loginId;
    @Pattern(regexp = "^[0-9a-zA-Z!@^*=+_-]{4,32}$")
    private String password;
}
