package com.example.masil.repository;

import com.example.masil.entity.Content;
import com.example.masil.entity.Progress;
import com.example.masil.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

//    boolean existsByUserAndContent(SiteUser siteUser, Content content);
    @Query("SELECT COUNT(p) > 0 FROM Progress p WHERE p.siteUser = :siteUser AND p.content = :content")
    boolean existsBySiteUserAndContent(@Param("siteUser") SiteUser siteUser, @Param("content") Content content);
    Optional<Progress> findBySiteUserAndContent(SiteUser siteUser, Content content);
    List<Progress> findBySiteUser(SiteUser siteUser);
}
