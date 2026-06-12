package com.example.masil.service;

import com.example.masil.config.Role;
import com.example.masil.dto.SiteUserDTO;
import com.example.masil.entity.OrderPay;
import com.example.masil.entity.SiteUser;
import com.example.masil.entity.Teacher;
import com.example.masil.exception.DataNotFoundException;
import com.example.masil.repository.OrderPayRepository;
import com.example.masil.repository.SiteUserRepository;
import com.example.masil.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final OrderPayRepository orderPayRepository;

    public Teacher findByLoginId(String loginId) {
        // 💡 끝에 .orElse(null)을 붙여서 데이터가 있으면 객체를, 없으면 null을 반환하게 만듭니다!
        return teacherRepository.findByLoginId(loginId).orElse(null);
    }
    // 1. 회원 리스트 (강사는 제외하고 불러오기)
    public Page<SiteUser> list(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    return siteUserRepository.findByRoleNotOrRoleIsNull(Role.INSTRUCTOR, pageable);
        // [수정] 필터링 없이 모든 유저(ADMIN, USER, INSTRUCTOR, null 전체)를 불러옵니다.
       // return siteUserRepository.findAll(pageable);
    }

    // SiteUserService.java의 modifyRole 메서드 수정
    @Transactional
    public void modifyRole(Long id, String roleStr) {
        SiteUser siteUser = siteUserRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));

        // 문자열을 Enum으로 변환
        Role newRole = Role.valueOf(roleStr);
        siteUser.setRole(newRole);
        siteUserRepository.save(siteUser); // 유저 권한 먼저 저장

        // [체크!] 로그를 찍어서 실제로 이 블록에 들어오는지 확인해보세요.
        if (newRole == Role.INSTRUCTOR) {
            System.out.println("강사 권한 확인됨! 테이블 등록 시작...");
            teacherService.autoRegister(siteUser);
        } else {
            System.out.println("강사 권한이 아님: " + newRole);
        }
    }

    // 3. 회원 정보 수정
    @Transactional
    public void sujungProc(SiteUserDTO siteUserDTO) {
        SiteUser siteUser = siteUserRepository.findById(siteUserDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("수정할 사용자를 찾을 수 없습니다."));

        siteUser.setName(siteUserDTO.getName());
        siteUser.setPhone(siteUserDTO.getPhone());
        siteUser.setBirth(siteUserDTO.getBirth());
        // 필요 시 비밀번호 변경 로직 추가
        siteUserRepository.save(siteUser);
    }

    // 4. 회원 삭제
    @Transactional
    public void sakjeProc(SiteUserDTO siteUserDTO) {
        SiteUser siteUser = siteUserRepository.findById(siteUserDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("삭제할 사용자를 찾을 수 없습니다."));
        siteUserRepository.delete(siteUser);
    }

    // 5. 회원 등록 (관리자용)
    @Transactional
    public void chugaProc(SiteUserDTO siteUserDTO) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(siteUserDTO.getUsername());
        siteUser.setName(siteUserDTO.getName());
        siteUser.setPassword(passwordEncoder.encode(siteUserDTO.getPassword()));
        siteUser.setPhone(siteUserDTO.getPhone());
        siteUser.setBirth(siteUserDTO.getBirth());
        siteUser.setRole(Role.USER);
        siteUserRepository.save(siteUser);
    }

    // --- 유틸리티 메서드들 ---
    public SiteUser view(Long id) {
        return siteUserRepository.findById(id).orElse(null);
    }

    public SiteUser getUser(String username) {
        return siteUserRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("user not found"));
    }

    public boolean isUsernameAvailable(String username) {
        return siteUserRepository.findByUsername(username).isEmpty();
    }

    // 연령대 차트 데이터
    public Map<String, Object> getAgeChartData() {
        List<SiteUser> allUsers = siteUserRepository.findAll();
        int age50Under = 0, age60s = 0, age70s = 0, age80Over = 0;
        int currentYear = LocalDate.now().getYear();

        for (SiteUser user : allUsers) {
            if (user.getBirth() != null && user.getBirth().length() >= 4) {
                int birthYear = Integer.parseInt(user.getBirth().substring(0, 4));
                int age = currentYear - birthYear;
                if (age < 60) age50Under++;
                else if (age < 70) age60s++;
                else if (age < 80) age70s++;
                else age80Over++;
            }
        }
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", Arrays.asList("50대 이하", "60대", "70대", "80대 이상"));
        chartData.put("data", Arrays.asList(age50Under, age60s, age70s, age80Over));
        return chartData;
    }
    public boolean existsByUsername(String username) {
        return siteUserRepository.existsByUsername(username);
    }

    // SiteUserService.java

    @Transactional
    public void syncInstructors() {
        // 1. role이 INSTRUCTOR인 모든 유저를 가져옴
        List<SiteUser> instructors = siteUserRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.INSTRUCTOR)
                .toList();

        for (SiteUser user : instructors) {
            // 2. teacherService를 통해 자동 등록 로직 실행
            // (이미 등록되어 있는지 체크하는 로직이 autoRegister 내부에 있어야 안전합니다)
            teacherService.autoRegister(user);
        }
        System.out.println("총 " + instructors.size() + "명의 강사 동기화 완료");
    }
    public List<SiteUser> getStudentList(Long categoryId) {
        return orderPayRepository.findByCategoryId(categoryId)
                .stream()
                .map(OrderPay::getSiteUser)
                .distinct()
                .collect(Collectors.toList());
    }

    public SiteUser getUserById(Long id) {
        return siteUserRepository.findById(id).orElse(null);
    }
}