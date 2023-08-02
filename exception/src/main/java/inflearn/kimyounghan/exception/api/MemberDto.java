package inflearn.kimyounghan.exception.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
    private final String id;
    private final String name;
}
