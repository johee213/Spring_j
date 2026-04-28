package com.example.sbb.service;

import com.example.sbb.dto.AnswerDTO;
import com.example.sbb.entity.Answer;
import com.example.sbb.entity.Question;
import com.example.sbb.entity.SiteUser;
import com.example.sbb.repository.AnswerRepository;
import com.example.sbb.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
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
        answer.setModifyDate(LocalDateTime.now());
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
        answer.setCreateDate(answerDTO.getCreateDate()); //
        answer.setModifyDate(LocalDateTime.now());
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

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        answerRepository.save(answer);
    }
}
