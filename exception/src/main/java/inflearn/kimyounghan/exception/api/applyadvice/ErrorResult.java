package inflearn.kimyounghan.exception.api.applyadvice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {
    private final String code;
    private final String message;
}
