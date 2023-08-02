package inflearn.kimyounghan.exception.api;

import inflearn.kimyounghan.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api2")
@RestController
public class ExceptionHandlerApiExceptionController {

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult resolveIllegalArgumentException(IllegalArgumentException e) {
        log.info("resolveIllegalArgumentException", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult resolveRuntimeException(RuntimeException e) {
        log.info("resolveRuntimeException", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> resolveUserException(UserException e) {
        log.info("resolveUserException", e);
        return new ResponseEntity<>(new ErrorResult("USER-EX", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Getter
    @AllArgsConstructor
    private static class ErrorResult {
        private final String code;
        private final String message;
    }

}