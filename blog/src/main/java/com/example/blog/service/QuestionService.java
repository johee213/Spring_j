package com.example.blog.service;


import com.example.blog.dto.QuestionDTO;
import com.example.blog.entity.Question;
import com.example.blog.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> list(){
       return questionRepository.findAll();
    }

    public Question view(QuestionDTO questionDTO){
        Question question = null;
        Optional<Question> questionOptional = questionRepository.findById(questionDTO.getId());

        if(questionOptional.isPresent())
        {
            question = questionOptional.get();
        }
        return question;
    }

    public void chugaProc(QuestionDTO questionDTO){
        Question question = createEntity(questionDTO);
        questionRepository.save(question);


    }
    public void sujungaProc(QuestionDTO questionDTO){
        Question question = createEntity(questionDTO);
        questionRepository.save(question);

    }



    public void sakjeProc(QuestionDTO questionDTO){
        Question question = createEntity(questionDTO);
        questionRepository.delete(question);

    }

    public Question createEntity(QuestionDTO questionDTO){
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setSubject(questionDTO.getSubject());
        question.setContent(questionDTO.getContent());
        question.setCreateDate(questionDTO.getCreateDate());
        return  question;


    }

}
