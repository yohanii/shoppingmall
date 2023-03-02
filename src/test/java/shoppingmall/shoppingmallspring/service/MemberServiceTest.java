package shoppingmall.shoppingmallspring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppingmall.shoppingmallspring.domain.Member;
import shoppingmall.shoppingmallspring.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void join() {
        //given
        Member member = new Member();
        member.setName("yohan");

        //when
        Long findId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(findId).get();
        assertThat("yohan").isEqualTo(findMember.getName());

    }

    @Test
    public void duplicatedJoin() {
        //given
        Member member1 = new Member();
        member1.setName("yohan");

        Member member2 = new Member();
        member2.setName("yohan");

        //when
//        try {
//            memberService.join(member1);
//            memberService.join(member2);
//            //fail();
//        } catch (Exception e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then

        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}
