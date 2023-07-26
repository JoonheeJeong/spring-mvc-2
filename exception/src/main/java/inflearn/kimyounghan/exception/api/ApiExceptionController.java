package inflearn.kimyounghan.exception.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable String id) {
        if (id.equals("ex"))
            throw new RuntimeException("잘못된 사용자");

        return new MemberDto(id, "Hello, " + id);
    }

    @Getter
    @AllArgsConstructor
    private static class MemberDto {

        private final String id;
        private final String name;
    }
}
