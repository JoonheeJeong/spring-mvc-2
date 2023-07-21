package hello.login.web.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class Member {

    private Long id;
    @Pattern(regexp = "^[0-9a-zA-Z_]{4,16}$")
    private String loginId;
    @Pattern(regexp = "^[0-9a-zA-Z!@^*=+_-]{4,32}$")
    private String password;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣]{4,32}$")
    private String name;
}
