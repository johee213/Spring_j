package com.meta12.yuni01.service;

import com.meta12.yuni01.dto.MemberDTO;
import com.meta12.yuni01.entity.Member;
import com.meta12.yuni01.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getSelectAll() { //전체목록
        List<Member> list = memberRepository.findAll();
        return list;
    }

    public Member getSelectOne(Long id) { //상세보기
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member member = null;
        if (optionalMember.isPresent()) {
            member = optionalMember.get();
        }
        return member;
    }



    public void setInsert(MemberDTO memberDTO) { //추가처리
        Member member = new Member();
        member.setUsername(memberDTO.getUsername());
        member.setPassword(memberDTO.getPassword());
        member.setPasswordChk(memberDTO.getPasswordChk());
        member.setName(memberDTO.getName());
        member.setSsn(memberDTO.getSsn());
        member.setPhone(memberDTO.getPhone());
        member.setEmail(memberDTO.getEmail());
        member.setAddress1(memberDTO.getAddress1());
        member.setAddress2(memberDTO.getAddress2());
        member.setAddress3(memberDTO.getAddress3());
        member.setAddress4(memberDTO.getAddress4());
        member.setGrade(memberDTO.getGrade());

        memberRepository.save(member);
    }

    public void setUpdate(MemberDTO memberDTO) { //수정처리
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setUsername(memberDTO.getUsername()); // null
        member.setPassword(memberDTO.getPassword());
        member.setPasswordChk(memberDTO.getPasswordChk());
        member.setName(memberDTO.getName()); // null
        member.setSsn(memberDTO.getSsn()); // null
        member.setPhone(memberDTO.getPhone());
        member.setEmail(memberDTO.getEmail());
        member.setAddress1(memberDTO.getAddress1());
        member.setAddress2(memberDTO.getAddress2());
        member.setAddress3(memberDTO.getAddress3());
        member.setAddress4(memberDTO.getAddress4());
        member.setGrade(memberDTO.getGrade());

        memberRepository.save(member);
    }

    public void setDelete(MemberDTO memberDTO) { //삭제처리
        Member member = new Member();
        member.setId(memberDTO.getId());

        memberRepository.delete(member);
    }

}
