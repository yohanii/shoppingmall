package shoppingmall.shoppingmallspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shoppingmall.shoppingmallspring.domain.Member;
import shoppingmall.shoppingmallspring.service.MemberService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String joinForm(MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String allMember(Model model) {
        List<Member> list = memberService.findMembers();
        model.addAttribute("members", list);

        return "members/memberList";
    }
}
