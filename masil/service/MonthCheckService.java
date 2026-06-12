package com.example.masil.service;


import com.example.masil.dto.MonthCheckDTO;
import com.example.masil.entity.MonthCheck;
import com.example.masil.entity.SiteUser;
import com.example.masil.exception.DataNotFoundException;
import com.example.masil.repository.MonthCheckRepository;
import com.example.masil.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
@RequiredArgsConstructor
public class MonthCheckService {

    private final MonthCheckRepository monthCheckRepository;
    private final SiteUserRepository siteUserRepository;

    public List<MonthCheck> list(SiteUser siteUser) {
        // 유저 정보를 기준으로 출석 기록만 깔끔하게 가져옵니다.
        return monthCheckRepository.findBySiteUser(siteUser);
    }
//    public MonthCheck view(Long id) {
//        Optional<MonthCheck> op = monthCheckRepository.findById(id);
//        MonthCheck monthCheck = null;
//        if(op.isPresent()){
//            monthCheck = op.get();
//        }
//        return monthCheck;
//    }

    public void chugaProc(String username) {
        SiteUser siteUser = siteUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        LocalDate today = LocalDate.now();
        List<MonthCheck> allChecks = monthCheckRepository.findBySiteUser(siteUser);

        // .toLocalDate()를 사용하여 시간 제외, 날짜만 정확히 비교
        boolean isAlreadyChecked = allChecks.stream()
                .filter(c -> c.getCheckDate() != null)
                .anyMatch(c -> c.getCheckDate().toLocalDate().isEqual(today));

        if (!isAlreadyChecked) {
            MonthCheck entity = new MonthCheck();
            entity.setSiteUser(siteUser);
            entity.setCheckDate(LocalDateTime.now());
            monthCheckRepository.save(entity);
        }
    }

    public void sujungProc(Long id, LocalDateTime newDate) {
        MonthCheck attendance = monthCheckRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("해당 출석 기록이 없습니다."));

        // 데이터 업데이트 (Dirty Checking 활용 또는 save)
        attendance.setCheckDate(newDate);
        monthCheckRepository.save(attendance);
    }

    public void sakjeProc(Long id) {
        if (monthCheckRepository.existsById(id)) {
            monthCheckRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("삭제할 기록이 존재하지 않습니다.");
        }
    }

    public MonthCheck entityMonthCheck(MonthCheckDTO monthCheckDTO, SiteUser siteUser) {

        MonthCheck monthCheck = new MonthCheck();

        monthCheck.setId(monthCheckDTO.getId());
        monthCheck.setSiteUser(monthCheckDTO.getSiteUser());
        monthCheck.setCheckDate(monthCheckDTO.getCheckDate());

        return monthCheck;
    }
}


