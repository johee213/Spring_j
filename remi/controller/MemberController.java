package com.example.remi.controller;

import com.example.remi.dto.MemberDTO;
import com.example.remi.entity.Member;
import com.example.remi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/list")
    public String list(
            Model model
    ) {
        List<Member> memberList = memberService.list();
        model.addAttribute("memberList", memberList);
        return "member/list";
    }

    @GetMapping("/member/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        Member member = memberService.view(id);
        if (member == null) {
            return "redirect:/";
        }
        model.addAttribute("member", member);
        return "member/view";
    }

    @GetMapping("/member/chuga")
    public String chuga(
            Model model
    ) {
        return "member/chuga";
    }

    @GetMapping("/member/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        Member member = memberService.view(id);
        if (member == null) {
            return "redirect:/";
        }
        model.addAttribute("member", member);
        return "member/sujung";
    }

    @GetMapping("/member/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        Member member = memberService.view(id);
        if (member == null) {
            return "redirect:/";
        }
        model.addAttribute("member", member);
        return "member/sakje";
    }

    @PostMapping("/member/chugaProc")
    public String chugaProc(
            MemberDTO memberDTO
    ) {
        if (!memberDTO.getPassword().equals(memberDTO.getPasswordChk())) {
            return "redirect:/";
        }

        //String ssn = memberDTO.getSsn1() + "-" + memberDTO.getSsn2();
        //memberDTO.setSsn(ssn);
        memberDTO.setSsn(memberDTO.getSsn1() + "-" + memberDTO.getSsn2());

        memberService.chugaProc(memberDTO);
        return "redirect:/member/list";
    }

    @PostMapping("/member/sujungProc")
    public String sujungProc(
            MemberDTO memberDTO
    ) {
        Member member = memberService.view(memberDTO.getId());
        if (member == null) {
            return "redirect:/";
        }

        if (!memberDTO.getPassword().equals(member.getPassword())) {
            return "redirect:/";
        }

        memberDTO.setRole(member.getRole().toString());

        memberService.sujungProc(memberDTO);
        return "redirect:/member/view/" + memberDTO.getId();
    }

    @PostMapping("/member/sakjeProc")
    public String sakjeProc(
            MemberDTO memberDTO
    ) {
        Member member = memberService.view(memberDTO.getId());
        if (member == null) {
            return "redirect:/";
        }

        memberService.sakjeProc(memberDTO);
        return "redirect:/member/list";
    }
}
