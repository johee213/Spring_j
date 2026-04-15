package com.example.sbb.controller;

import com.example.sbb.dto.AnswerDTO;
import com.example.sbb.entity.Question;
import com.example.sbb.service.AnswerService;
import com.example.sbb.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/answer/chugaProc")
    public String chugaProc(
            Model model,
            @Valid AnswerDTO answerDTO,
            BindingResult bindingResult
    ) {
        Question question = questionService.view(answerDTO.getQuestionId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question/view";
        }

        answerService.chugaProc(answerDTO);
        return "redirect:/question/view/" + answerDTO.getQuestionId();
    }

}
