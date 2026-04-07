package com.meta12.yuni01.controller;

import com.meta12.yuni01.dto.MemberDTO;
import com.meta12.yuni01.entity.Member;
import com.meta12.yuni01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/list")
    public String list(Model model) {
        List<Member> list = memberService.getSelectAll();
        model.addAttribute("list", list);
        return "member/list";
    }

    @GetMapping("/member/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        Member member = memberService.getSelectOne(id);
        if (member == null) {
            model.addAttribute("errorCode", "Error001");
            model.addAttribute("errorMsg", "존재하지 않는 회원입니다.");
            return "error/error";
        }
        model.addAttribute("member", member);
        return "member/view";
    }

    @GetMapping("/member/chuga")
    public String chuga() {
        return "member/chuga";
    }

    @GetMapping("/member/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        Member member = memberService.getSelectOne(id);
        if (member == null) {
            model.addAttribute("errorCode", "Error001");
            model.addAttribute("errorMsg", "존재하지 않는 회원입니다.");
            return "error/error";
        }
        model.addAttribute("member", member);
        return "member/sujung";
    }

    @GetMapping("/member/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        Member member = memberService.getSelectOne(id);
        if (member == null) {
            model.addAttribute("errorCode", "Error001");
            model.addAttribute("errorMsg", "존재하지 않는 회원입니다.");
            return "error/error";
        }
        model.addAttribute("member", member);
        return "member/sakje";
    }

    @PostMapping("/member/chugaProc")
    public String chugaProc(
            MemberDTO memberDTO
    ) {
        memberService.setInsert(memberDTO);
        return "redirect:/member/list";
    }

    @PostMapping("/member/sujungProc")
    public String sujungProc(
            MemberDTO memberDTO //3
    ) {
/*
        Member member = memberService.getSelectOne(memberDTO.getId());
        if (member == null) {
            return "redirect:/member/list";
        }

        if (memberDTO.getPassword().equals(member.getPassword())) { //정상

        } else { //비번 안맞음
            return "redirect:/member/sujung/" + memberDTO.getId();
        }

        memberDTO.setUsername(member.getUsername());
        memberDTO.setName(member.getName());
        memberDTO.setSsn(member.getSsn());
*/
        memberService.setUpdate(memberDTO);
        return "redirect:/member/view/" + memberDTO.getId();
    }

    @PostMapping("/member/sakjeProc")
    public String sakjeProc(
            MemberDTO memberDTO
    ) {
        Member member = memberService.getSelectOne(memberDTO.getId());
        if (member == null) {
            return "redirect:/member/list";
        }

        if (memberDTO.getPassword().equals(member.getPassword())) { //정상

        } else { //비번 안맞음
            return "redirect:/member/view/" + memberDTO.getId();
        }

        memberService.setDelete(memberDTO);
        return "redirect:/member/list";
    }

}
