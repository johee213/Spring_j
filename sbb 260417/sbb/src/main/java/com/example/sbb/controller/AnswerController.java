package com.example.sbb.controller;

import com.example.sbb.dto.AnswerDTO;
import com.example.sbb.entity.Question;
import com.example.sbb.entity.SiteUser;
import com.example.sbb.service.AnswerService;
import com.example.sbb.service.QuestionService;
import com.example.sbb.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final SiteUserService siteUserService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/chugaProc")
    public String chugaProc(
            Model model,
            @Valid AnswerDTO answerDTO,
            BindingResult bindingResult,
            Principal principal
    ) {
        Question question = questionService.view(answerDTO.getQuestionId());
        SiteUser siteUser = siteUserService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question/view";
        }

        answerService.chugaProc(answerDTO, siteUser);
        return "redirect:/question/view/" + answerDTO.getQuestionId();
    }

}
