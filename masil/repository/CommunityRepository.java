package com.example.masil.repository;

import com.example.masil.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    // 최신순 정렬
    List<Community> findAllByOrderByCreateDateDesc();

    // 오래된순 정렬
    List<Community> findAllByOrderByCreateDateAsc();
}