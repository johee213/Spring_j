package com.example.masil.repository;

import com.example.masil.config.Role;
import com.example.masil.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SiteUserRepository extends JpaRepository <SiteUser, Long> {
        Optional<SiteUser> findByUsername(String username);
        // 이 줄을 추가하면 DB를 더 효율적으로 조회합니다.
        boolean existsByUsername(String username);
        // 추가: 특정 권한(INSTRUCTOR)이 아닌 사용자만 페이징해서 가져오기
        Page<SiteUser> findByRoleNot(Role role, Pageable pageable);

        // 만약 Role이 null인 사용자(가입 직후 등)도 포함해야 한다면 아래와 같이 쓸 수도 있습니다.
        org.springframework.data.domain.Page<SiteUser> findByRoleNotOrRoleIsNull(com.example.masil.config.Role role, org.springframework.data.domain.Pageable pageable);

//        List<SiteUser> findByRoleAndCategory(Role role, String category);

        @Query("SELECT DISTINCT s FROM SiteUser s JOIN s.orderPayList o WHERE o.category.id = :categoryId")
        List<SiteUser> findStudentsByCategoryId(@Param("categoryId") Long categoryId);
        List<SiteUser> findByRole(Role role);
}
