package shoppingmall.shoppingmallspring.repository;

import org.springframework.stereotype.Repository;
import shoppingmall.shoppingmallspring.domain.Member;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //동시성문제가 있을 수 있어서 실무에서는 concurrent HashMap을 쓴다고 함.
    private static long sequence = 0L; //이것도 동시성 문제때문에 실무에서는 AtomicLong을 쓴다고 함.

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = store.get(id);

        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        Optional<Member> member = store.values().stream()
                .filter(m -> m.getName().equals(name))
                .findAny();

        return member;
    }

    @Override
    public List<Member> findAll() {
        List<Member> list = new ArrayList<>(store.values());

        return list;
    }

    public void clearStore() {
        store.clear();
    }
}
