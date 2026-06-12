package com.example.masil.repository;

import com.example.masil.entity.SiteUser;
import com.example.masil.entity.Teacher;
import org.springframework.context.annotation.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByCategory(String category);
    List<Teacher> findByTeacherNameContaining(String teacherName);
    List<Teacher> findByCategoryAndTeacherNameContaining(String category, String teacherName);
    Optional<Teacher> findByLoginId(String loginId);
    // 추가: 특정 권한(INSTRUCTOR)을 가진 강사만 조회
    List<Teacher> findByRole(Role role);

    // 검색 기능과 결합하고 싶다면 아래와 같이 활용 (선택 사항)
    List<Teacher> findByRoleAndCategoryContaining(Role role, String category);

    List<SiteUser> findByRoleAndCategory(com.example.masil.config.Role role, String category);
    boolean existsByLoginId(String loginId);

}

