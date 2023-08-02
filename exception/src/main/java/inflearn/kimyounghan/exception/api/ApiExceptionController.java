package inflearn.kimyounghan.exception.api;

import inflearn.kimyounghan.exception.exception.BadRequestException;
import inflearn.kimyounghan.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequestMapping("/api")
@RestController
public class ApiExceptionController {

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

    @GetMapping("/response-status-ex1")
    public void throwExceptionAnnotatedResponseStatus() {
        throw new BadRequestException();
    }

    @GetMapping("/response-status-ex2")
    public void throwResponseStatusException() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.bad", new IllegalArgumentException());
    }

    @GetMapping("/default-handler-ex")
    public String occurTypeMismatchException(@RequestParam Integer data) {
        return "ok";
    }

}
