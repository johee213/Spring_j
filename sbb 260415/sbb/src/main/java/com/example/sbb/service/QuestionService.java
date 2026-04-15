package com.example.sbb.service;

import com.example.sbb.dto.QuestionDTO;
import com.example.sbb.entity.Question;
import com.example.sbb.repository.QuestionRepository;
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

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> list(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    public Question view(Long id) {
        Optional<Question> oq = questionRepository.findById(id);
        Question question = null;
        if (oq.isPresent()) {
            question = oq.get();
        }
        return question;
    }

    public void chugaProc(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setSubject(questionDTO.getSubject());
        question.setContent(questionDTO.getContent());
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }

//    public void sujungProc() {
//
//    }

//    public void sakjeProc() {
//
//    }
}
