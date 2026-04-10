package com.example.remi.service;

import com.example.remi.constant.Role;
import com.example.remi.dto.MemberDTO;
import com.example.remi.entity.Member;
import com.example.remi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private Member createEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setUsername(memberDTO.getUsername());
        member.setPassword(memberDTO.getPassword());
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setSsn(memberDTO.getSsn());
        member.setAddress1(memberDTO.getAddress1());
        member.setAddress2(memberDTO.getAddress2());
        member.setAddress3(memberDTO.getAddress3());
        member.setAddress4(memberDTO.getAddress4());
        member.setRole(Role.valueOf(memberDTO.getRole()));

//        Member member = Member.builder()
//                .id(memberDTO.getId())
//                .username(memberDTO.getUsername())
//                .password(memberDTO.getPassword())
//                .name(memberDTO.getName())
//                .email(memberDTO.getEmail())
//                .ssn(memberDTO.getSsn())
//                .address1(memberDTO.getAddress1())
//                .address2(memberDTO.getAddress2())
//                .address3(memberDTO.getAddress3())
//                .address4(memberDTO.getAddress4())
//                .role(Role.valueOf(memberDTO.getRole()))
//                .build();

        return member;
    }

    public List<Member> list() {
        return memberRepository.findAll();
    }

    public Member view(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member member = null;
        if (optionalMember.isPresent()) {
            member = optionalMember.get();
        }
        return member;
    }

    public void chugaProc(MemberDTO memberDTO) {
        Member member = this.createEntity(memberDTO);
        memberRepository.save(member);
        System.out.println("---> " + member.getId());
    }

    public void sujungProc(MemberDTO memberDTO) {
        Member member = this.createEntity(memberDTO);
        memberRepository.save(member);
        System.out.println("---> " + member.getId());
    }

    public void sakjeProc(MemberDTO memberDTO) {
        Member member = this.createEntity(memberDTO);
        memberRepository.delete(member);
        System.out.println("---> " + member.getId());
    }
}
