package com.example.sbb.controller;

import com.example.sbb.dto.AnswerDTO;
import com.example.sbb.dto.QuestionDTO;
import com.example.sbb.entity.Question;
import com.example.sbb.entity.SiteUser;
import com.example.sbb.repository.QuestionRepository;
import com.example.sbb.service.QuestionService;
import com.example.sbb.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final SiteUserService siteUserService;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/chuga")
    public String chuga(
            Model model,
            QuestionDTO questionDTO
    ) {
        return "question/chuga";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/chugaProc")
    public String chugaProc(
            @Valid QuestionDTO questionDTO,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "question/chuga"; //forwarding
        }

        SiteUser siteUser = siteUserService.getUser(principal.getName());
        questionService.chugaProc(questionDTO, siteUser);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") long id,
            QuestionDTO questionDTO,
            Principal principal
    ) {
        Question question = questionService.view(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionDTO.setId(question.getId());
        questionDTO.setSubject(question.getSubject());
        questionDTO.setContent(question.getContent());
        questionDTO.setCreateDate(question.getCreateDate());

        model.addAttribute("questionDTO", questionDTO);
        return "question/sujung";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/sujungProc")
    public String sujungProc(
            @Valid QuestionDTO questionDTO,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "question/sujung"; //forwarding
        }

        Question question = questionService.view(questionDTO.getId());
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionDTO.setCreateDate(question.getCreateDate());

        questionService.sujungProc(questionDTO, question.getAuthor());
        return "redirect:/question/view/" + question.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") long id,
            QuestionDTO questionDTO,
            Principal principal
    ) {
        Question question = questionService.view(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        questionDTO.setId(question.getId());
        questionDTO.setSubject(question.getSubject());
        questionDTO.setContent(question.getContent());
        questionDTO.setCreateDate(question.getCreateDate());

        model.addAttribute("questionDTO", questionDTO);
        return "question/sakje";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/sakjeProc")
    public String sakjeProc(
            //@Valid QuestionDTO questionDTO,
            //BindingResult bindingResult,
            @RequestParam("id") Long id,
            Principal principal
    ) {
        Question question = questionService.view(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);

        questionService.sakjeProc(questionDTO, question.getAuthor());
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/vote/{id}")
    public String vote(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Question question = questionService.view(id);
        if (question == null) {
            return "redirect:/";
        }
        SiteUser siteUser = siteUserService.getUser(principal.getName());
        questionService.vote(question, siteUser);
        return "redirect:/question/view/" + id;
    }

}
