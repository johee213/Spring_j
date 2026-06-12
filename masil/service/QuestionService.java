package com.example.masil.service;


import com.example.masil.dto.QuestionDTO;
import com.example.masil.entity.Question;
import com.example.masil.entity.SiteUser;
import com.example.masil.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> list(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));

        if (kw == null || kw.trim().isEmpty()) {
            return questionRepository.findAll(pageable);
        } else {
            return questionRepository.findBySubjectContaining(kw, pageable);
        }
    }

    public Question view(Long id) {
        Optional<Question> oq = questionRepository.findById(id);
        Question question = null;
        if (oq.isPresent()) {
            question = oq.get();
        }
        return question;
    }


    public void chugaProc(QuestionDTO questionDTO, SiteUser siteUser) {
        questionRepository.save(createEntity(questionDTO, siteUser));
    }

    public void sujungProc(QuestionDTO questionDTO, SiteUser siteUser) {
        questionRepository.save(createEntity(questionDTO, siteUser));
    }

    public void sakjeProc(QuestionDTO questionDTO, SiteUser siteUser) {
        questionRepository.delete(createEntity(questionDTO, siteUser));
    }

    private Question createEntity(QuestionDTO questionDTO, SiteUser siteUser) {
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setSubject(questionDTO.getSubject());
        question.setContent(questionDTO.getContent());

        question.setCreateDate(LocalDateTime.now());
        if (questionDTO.getId() != null) {
            question.setCreateDate(questionDTO.getCreateDate());
        }

        question.setAuthor(siteUser);
        return question;
    }

    // QuestionService.java

    public void modify(Question question, String subject, String content) {
        // 1. 전달받은 제목과 내용으로 질문 엔티티의 데이터를 변경합니다.
        question.setSubject(subject);
        question.setContent(content);


        // 3. 변경된 내용을 DB에 저장합니다.
        this.questionRepository.save(question);
    }

    public Page<Question> getMyList(int page, SiteUser author) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 내역은 조금 더 많이 보여줘도 좋습니다.
        return this.questionRepository.findByAuthor(author, pageable);
    }
}
