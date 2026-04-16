package com.example.sbb.controller;

import com.example.sbb.dto.AnswerDTO;
import com.example.sbb.dto.QuestionDTO;
import com.example.sbb.entity.Question;
import com.example.sbb.repository.QuestionRepository;
import com.example.sbb.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(
            Model model,
            @RequestParam(value="page", defaultValue="0") int page
    ) {
        Page<Question> paging = questionService.list(page);
        model.addAttribute("paging", paging);
        return "question/list";
    }

    @GetMapping("/question/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id,
            AnswerDTO answerDTO
    ) {
        Question question = questionService.view(id);
        model.addAttribute("question", question);
        return "question/view";
    }

    @GetMapping("/question/chuga")
    public String chuga(
            Model model,
            QuestionDTO questionDTO
    ) {
        return "question/chuga";
    }

    @PostMapping("/question/chugaProc")
    public String chugaProc(
            @Valid QuestionDTO questionDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "question/chuga"; //forwarding
        }

        questionService.chugaProc(questionDTO);
        return "redirect:/question/list";
    }

}
