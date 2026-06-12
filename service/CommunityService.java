package com.example.masil.service;

import com.example.masil.entity.Community;
import com.example.masil.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final CommunityRepository communityRepository;

    // 최신순 조회
    public List<Community> getList() {
        return communityRepository.findAllByOrderByCreateDateDesc();
    }

    // 오래된순 조회 (추가)
    public List<Community> getListAsc() {
        return communityRepository.findAllByOrderByCreateDateAsc();
    }

    public Community getCommunity(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    @Transactional
    public void save(Community community) {
        if (community.getCreateDate() == null) {
            community.setCreateDate(LocalDateTime.now());
        }
        communityRepository.save(community);
    }

    @Transactional
    public void modify(Long id, String content, String username) {
        Community community = getCommunity(id);
        if (!community.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        community.setContent(content);
    }

    @Transactional
    public void delete(Long id, Authentication authentication) {
        Community community = getCommunity(id);
        String username = authentication.getName();

        // 1. 관리자 권한 확인 (ROLE_ADMIN 권한을 가지고 있는지 확인)
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // 2. 작성자가 아니면서 관리자도 아닐 때만 예외 발생
        if (!community.getAuthor().getUsername().equals(username) && !isAdmin) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        communityRepository.delete(community);
    }
}