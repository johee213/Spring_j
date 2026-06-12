package com.example.masil.service;

import com.example.masil.config.Role;
import com.example.masil.dto.TeacherDTO;
import com.example.masil.entity.OrderPay;
import com.example.masil.entity.SiteUser;
import com.example.masil.entity.Teacher;
import com.example.masil.repository.OrderPayRepository;
import com.example.masil.repository.SiteUserRepository;
import com.example.masil.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final OrderPayRepository orderPayRepository;
    private final SiteUserRepository siteUserRepository;

    private Teacher creatEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setTeacherName(teacherDTO.getTeacherName());
        teacher.setLoginId(teacherDTO.getLoginId());
        teacher.setPassword(teacherDTO.getPassword());
        teacher.setPhone(teacherDTO.getPhone());
        teacher.setBirth(teacherDTO.getBirth());
        teacher.setNote(teacherDTO.getNote());
        teacher.setCategory(teacherDTO.getCategory());

        // [추가] 강사 등록 시 권한을 INSTRUCTOR로 강제 설정
        teacher.setRole(com.example.masil.config.Role.INSTRUCTOR);

        return teacher;
    }

    // TeacherService.java 수정

    public List<Teacher> list() {
        return teacherRepository.findAll().stream()
                .filter(t -> t.getRole() == com.example.masil.config.Role.INSTRUCTOR)
                .toList(); // [핵심] Stream을 다시 List로 변환
    }

    public List<Teacher> list(String category, String teacherName) {
        List<Teacher> result;

        if (category != null && !category.isEmpty() && teacherName != null && !teacherName.isEmpty()) {
            result = teacherRepository.findByCategoryAndTeacherNameContaining(category, teacherName);
        } else if (category != null && !category.isEmpty()) {
            result = teacherRepository.findByCategory(category);
        } else if (teacherName != null && !teacherName.isEmpty()) {
            result = teacherRepository.findByTeacherNameContaining(teacherName);
        } else {
            result = teacherRepository.findAll();
        }

        // 결과 필터링 후 반드시 .toList() 호출
        return result.stream()
                .filter(t -> t.getRole() == com.example.masil.config.Role.INSTRUCTOR)
                .toList(); // [핵심] Stream을 다시 List로 변환
    }


    public Teacher view(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Transactional
    public void chugaProc(TeacherDTO teacherDTO) {
        Teacher teacher = this.creatEntity(teacherDTO);
        teacherRepository.save(teacher);
    }

    @Transactional
    public void sujungProc(TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findById(teacherDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("강사를 찾을 수 없습니다."));

        teacher.setTeacherName(teacherDTO.getTeacherName());
        teacher.setCategory(teacherDTO.getCategory());
        teacher.setPhone(teacherDTO.getPhone());
        teacher.setNote(teacherDTO.getNote());
        teacher.setBirth(teacherDTO.getBirth());

        // 만약 새 비밀번호(passwordChk 등)를 입력했다면 암호화해서 저장하는 로직을 추가할 수 있습니다.

        teacherRepository.save(teacher);
    }

    @Transactional
    public void sakjeProc(TeacherDTO teacherDTO) {
        teacherRepository.deleteById(teacherDTO.getId());
    }

    @Transactional
    public void autoRegister(SiteUser user) {
        // Optional로 올바르게 중복 체크
        if (!teacherRepository.findByLoginId(user.getUsername()).isPresent()) { // ← 수정
            Teacher teacher = new Teacher();
            teacher.setTeacherName(user.getName());
            teacher.setLoginId(user.getUsername());
            teacher.setPassword(user.getPassword());
            teacher.setRole(Role.INSTRUCTOR);
            teacher.setCategory("미지정");
            teacher.setBirth(user.getBirth() != null ? user.getBirth() : "1900-01-01");
            teacher.setPhone(user.getPhone() != null ? user.getPhone() : "010-0000-0000");

            teacherRepository.save(teacher);
            System.out.println("강사 테이블에 신규 등록 완료: " + user.getUsername());
        } else {
            System.out.println("이미 등록된 강사: " + user.getUsername());
        }
    }
    public Teacher findByLoginId(String loginId) {
        // 💡 끝에 .orElse(null)을 붙여주면 상자 안의 Teacher 객체가 쏙 빠져나와 에러가 즉시 해결됩니다!
        return teacherRepository.findByLoginId(loginId).orElse(null);
    }

    public List<SiteUser> getStudentList() {
        // Role이 USER인 경우만 가져오기
        return siteUserRepository.findByRole(Role.USER);
    }
}