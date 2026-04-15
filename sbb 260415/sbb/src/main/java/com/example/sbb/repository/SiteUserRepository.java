package com.example.sbb.repository;

import com.example.sbb.entity.Question;
import com.example.sbb.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    //import org.springframework.data.domain.Page;
    //import org.springframework.data.domain.Pageable;
    //Page<SiteUser> findAll(Pageable pageable);
}
