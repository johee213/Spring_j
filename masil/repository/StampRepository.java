package com.example.masil.repository;

import com.example.masil.entity.SiteUser;
import com.example.masil.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long> {
    // 특정 학생이 받은 도장 리스트를 가져오는 메서드 (필요시)
    List<Stamp> findBySiteUser_Username(String username);
    List<Stamp> findBySiteUser(SiteUser siteUser);
}


