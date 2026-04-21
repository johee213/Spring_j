package com.example.blog.service;

import com.example.blog.dto.MemberDTO;
import com.example.blog.entity.Member;
import com.example.blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> list() {
        return memberRepository.findAll();
    }

    public Member view(MemberDTO memberDTO) {
        Member member = null;
        Optional<Member> optionalMember = memberRepository.findById(memberDTO.getId());
        if (optionalMember.isPresent()) {
            member = optionalMember.get();
        }
        return member;
    }

    public void chugaProc(MemberDTO memberDTO) {
        Member member = createEntity(memberDTO);
        memberRepository.save(member);
    }

    public void sujungProc(MemberDTO memberDTO) {
        memberRepository.save(createEntity(memberDTO));
    }

    public void sakjeProc(MemberDTO memberDTO) {
        memberRepository.save(createEntity(memberDTO));
    }

    private Member createEntity(MemberDTO memberDTO) {
        Member member =  new Member();
        member.setId(memberDTO.getId());
        member.setUsername(memberDTO.getUsername());
        member.setPassword(memberDTO.getPassword());
        member.setEmail(memberDTO.getEmail());
        member.setRole(memberDTO.getRole());
        member.setCreateDate(memberDTO.getCreateDate());
        return member;
    }
}
