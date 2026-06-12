package com.example.masil.service;

import com.example.masil.entity.SiteUser;
import com.example.masil.entity.Stamp;
import com.example.masil.repository.StampRepository;
import com.example.masil.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StampService {
    private final StampRepository stampRepository;
    private final SiteUserRepository siteUserRepository;

    @Transactional
    public void giveStamp(Long studentId, String teacherUsername) {
        SiteUser student = siteUserRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        // 1. Stamp 객체 생성 및 저장
        Stamp stamp = new Stamp();
        stamp.setSiteUser(student);
        stamp.setStampDate(LocalDateTime.now());
        // 필요시 teacher 정보도 설정
        stampRepository.save(stamp);

        // 2. 학생의 도장 카운트 증가 (SiteUser 엔티티에 필드가 있다면)
        student.setStampCount(student.getStampCount() + 1);
        siteUserRepository.save(student);
    }

    public List<Stamp> findBySiteUser(SiteUser siteUser) {
        return stampRepository.findBySiteUser(siteUser);
    }
}