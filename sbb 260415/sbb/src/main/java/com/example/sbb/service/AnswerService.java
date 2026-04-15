package com.example.sbb.service;

import com.example.sbb.dto.AnswerDTO;
import com.example.sbb.entity.Answer;
import com.example.sbb.entity.Question;
import com.example.sbb.repository.AnswerRepository;
import com.example.sbb.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public void chugaProc(AnswerDTO answerDTO) {
        Question question = questionService.view(answerDTO.getQuestionId());

        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);

        answerRepository.save(answer);
    }
}
