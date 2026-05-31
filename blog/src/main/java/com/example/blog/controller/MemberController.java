package com.example.blog.controller;

import com.example.blog.dto.MemberDTO;
import com.example.blog.entity.Member;
import com.example.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(
            Model model
    ) {
        List<Member> list = memberService.list();
        model.addAttribute("list", list);
        return "member/list";
    }

    @GetMapping("/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);

        Member member = memberService.view(memberDTO);
        if (member == null) {
            return "redirect:/";
        }

        model.addAttribute("member", member);
        return "member/view";
    }

    @GetMapping("/chuga")
    public String chuga(
            Model model
    ) {
        return "member/chuga";
    }

    @PostMapping("/chugaProc")
    public String chugaProc(
            MemberDTO memberDTO
    ) {
        memberService.chugaProc(memberDTO);
        return "redirect:/member/list";
    }

    @GetMapping("/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);

        Member member = memberService.view(memberDTO);
        if (member == null) {
            return "redirect:/";
        }

        model.addAttribute("member", member);
        return "member/sujung";
    }

    @PostMapping("/sujungProc")
    public String sujungProc(
            MemberDTO memberDTO
    ) {
        Member member = memberService.view(memberDTO);
        if (member == null) {
            return "redirect:/";
        }
        memberService.sujungProc(memberDTO);
        return "redirect:/member/view/" + member.getId();
    }

    @GetMapping("/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);

        Member member = memberService.view(memberDTO);
        if (member == null) {
            return "redirect:/";
        }
        model.addAttribute("member", member);
        return "member/sakje";
    }

    @PostMapping("/sakjeProc")
    public String sakjeProc(
            MemberDTO memberDTO
    ) {
        Member member = memberService.view(memberDTO);
        if (member == null) {
            return "redirect:/";
        }
        memberService.sakjeProc(memberDTO);
        return "redirect:/member/list";
    }
}
