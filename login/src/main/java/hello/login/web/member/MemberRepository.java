package hello.login.web.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private final Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        log.info("save:  {}", member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return store.values().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
