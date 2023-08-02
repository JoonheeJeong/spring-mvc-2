package inflearn.kimyounghan.exception.exception.advice;

import inflearn.kimyounghan.exception.api.applyadvice.ErrorResult;
import inflearn.kimyounghan.exception.api.applyadvice.ExceptionHandlerApiExceptionController;
import inflearn.kimyounghan.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = ExceptionHandlerApiExceptionController.class)
public class ExControllerAdvice {

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

}
