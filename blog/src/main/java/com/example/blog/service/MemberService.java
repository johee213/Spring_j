package com.example.blog.service;


import com.example.blog.config.Role;
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


    // 디비 전체 다갖고와서 토스
    public List<Member> list(){
        return memberRepository.findAll();

    }
//레코드 한개만
    public Member view(MemberDTO memberDTO){
        Member member = null;
        Optional<Member> optionalMember =  memberRepository.findById(memberDTO.getId());

        if(optionalMember.isPresent());
        {
            member = optionalMember.get();
        }
        return member;
    }


    //dto 를 entitiy로 변환
    public void chugaProc(MemberDTO memberDTO) {
        Member member = createEntity(memberDTO);
        memberRepository.save(member);
    }

    public void sujungProc(MemberDTO memberDTO){
        memberRepository.save(createEntity(memberDTO));

    }

//    추가처리할일이 없어서 Proc 사용
    public void sakjeProc(MemberDTO memberDTO){
       Member member = createEntity(memberDTO);
       memberRepository.delete(member);
    }

    private Member createEntity(MemberDTO memberDTO){
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setUsername(memberDTO.getUsername());
        member.setPassword(memberDTO.getPassword());
        member.setEmail(memberDTO.getEmail());
        //회원가입하면 강제로 일반으로 넣겟다
        member.setRole(Role.USER);
        member.setCreateDate(memberDTO.getCreateDate());

        return member;

    }






}
