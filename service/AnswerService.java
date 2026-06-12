package com.example.masil.service;


import com.example.masil.dto.AnswerDTO;
import com.example.masil.entity.Answer;
import com.example.masil.entity.Question;
import com.example.masil.entity.SiteUser;
import com.example.masil.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public Answer view(Long id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        Answer answer = null;
        if (optionalAnswer.isPresent()) {
            answer = optionalAnswer.get();
        }
        return answer;
    }


    public Answer chugaProc(AnswerDTO answerDTO, SiteUser siteUser) {
        Question question = questionService.view(answerDTO.getQuestionId());

        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(siteUser);

        answerRepository.save(answer);

        return answer;
    }

    public Answer sujungProc(AnswerDTO answerDTO, SiteUser siteUser) {
        Question question = questionService.view(answerDTO.getQuestionId());

        Answer answer = new Answer();
        answer.setId(answerDTO.getId());
        answer.setContent(answerDTO.getContent());
        answer.setCreateDate(answerDTO.getCreateDate());
        answer.setQuestion(question);
        answer.setAuthor(siteUser);

        answerRepository.save(answer);

        return answer;
    }

    public Answer sakjeProc(AnswerDTO answerDTO, SiteUser siteUser) {
        Question question = questionService.view(answerDTO.getQuestionId());

        Answer answer = new Answer();
        answer.setId(answerDTO.getId());
        answer.setContent(answerDTO.getContent());
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(siteUser);

        answerRepository.delete(answer);

        return answer;
    }



}
