package com.example.masil.service;

import com.example.masil.entity.SiteUser;
import com.example.masil.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final SiteUserRepository siteUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. DB에서 사용자 조회
        SiteUser siteUser = siteUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 2. 권한 설정[cite: 4]
        List<GrantedAuthority> authorities = new ArrayList<>();

// 💡 DB의 role 필드 값(Enum 문자열)을 가져와 비교합니다.
        if ("ADMIN".equals(siteUser.getRole().name())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if ("INSTRUCTOR".equals(siteUser.getRole().name())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR")); // 🔥 드디어 강사 권한 제대로 배달!
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // 3. 시큐리티 User 객체 반환 (아이디, 암호화된 비번, 권한)
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}