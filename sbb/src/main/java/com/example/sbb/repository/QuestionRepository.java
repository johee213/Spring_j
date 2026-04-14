package com.example.sbb.repository;

import com.example.sbb.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    //쿼리메소드
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);

    //import org.springframework.data.domain.Page;
    //import org.springframework.data.domain.Pageable;
    Page<Question> findAll(Pageable pageable);
}
