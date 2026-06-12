package com.example.masil.repository;


import com.example.masil.entity.Question;
import com.example.masil.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    Page<Question> findByAuthor(SiteUser author, Pageable pageable);

    // 추가: 제목에 키워드가 포함된 질문 리스트 페이징 처리
    Page<Question> findBySubjectContaining(String kw, Pageable pageable);
}
