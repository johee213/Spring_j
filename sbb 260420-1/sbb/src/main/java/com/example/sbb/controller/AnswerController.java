package com.example.sbb.controller;

import com.example.sbb.dto.AnswerDTO;
import com.example.sbb.entity.Answer;
import com.example.sbb.entity.Question;
import com.example.sbb.entity.SiteUser;
import com.example.sbb.service.AnswerService;
import com.example.sbb.service.QuestionService;
import com.example.sbb.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/answer/sujung/{id}")
    public String sujung(
            @PathVariable("id") Long id,
            AnswerDTO answerDTO,
            Principal principal
    ) {
        Answer answer = answerService.view(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerDTO.setId(id);
        answerDTO.setContent(answer.getContent());
        return "answer/sujung";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/sujungProc")
    public String sujungProc(
            @Valid AnswerDTO answerDTO,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "answer/sujung";
        }
        Answer answer = answerService.view(answerDTO.getId());
        if (answer == null) {
            //해당 글이 없다.
        }
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerDTO.setCreateDate(answer.getCreateDate());
        answerDTO.setQuestionId(answer.getQuestion().getId());

        SiteUser siteUser = siteUserService.getUser(principal.getName());

        answerService.sujungProc(answerDTO, siteUser);
        return "redirect:/question/view/" + answerDTO.getQuestionId();
    }

    @GetMapping("/answer/sakje/{id}")
    public String sakje(
            @PathVariable("id") Long id,
            AnswerDTO answerDTO,
            Principal principal
    ) {
        Answer answer = answerService.view(id);
        if (answer == null) {
            return "redirect:/";
        }
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        answerDTO.setId(id);
        answerDTO.setContent(answer.getContent());
        answerDTO.setQuestionId(answer.getQuestion().getId());
        return "answer/sakje";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/sakjeProc")
    public String sakjeProc(
            @Valid AnswerDTO answerDTO,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "answer/sakje";
        }
        Answer answer = answerService.view(answerDTO.getId());
        if (answer == null) {
            return "redirect:/";
        }
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        answerDTO.setQuestionId(answer.getQuestion().getId());

        SiteUser siteUser = siteUserService.getUser(principal.getName());

        answerService.sakjeProc(answerDTO, siteUser);
        return "redirect:/question/view/" + answerDTO.getQuestionId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/vote/{id}")
    public String vote(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Answer answer = answerService.view(id);
        if (answer == null) {
            return "redirect:/";
        }
        SiteUser siteUser = siteUserService.getUser(principal.getName());
        answerService.vote(answer, siteUser);
        return "redirect:/question/view/" + answer.getQuestion().getId();
    }
}
