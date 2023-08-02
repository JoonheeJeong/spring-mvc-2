package inflearn.kimyounghan.exception.api.applyadvice;

import inflearn.kimyounghan.exception.api.MemberDto;
import inflearn.kimyounghan.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api3")
@RestController
public class ExceptionHandlerApiExceptionControllerV2 {

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable String id) {
        switch (id) {
            case "ex":
                throw new RuntimeException("잘못된 사용자");
            case "bad":
                throw new IllegalArgumentException("잘못된 입력");
            case "user-ex":
                throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "Hello, " + id);
    }

}