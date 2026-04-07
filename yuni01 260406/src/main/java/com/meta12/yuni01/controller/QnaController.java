package com.meta12.yuni01.controller;

import com.meta12.yuni01.dto.QnaDTO;
import com.meta12.yuni01.entity.Qna;
import com.meta12.yuni01.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QnaController {

    private final QnaService qnaService;

    @GetMapping("/qna/list")
    public String list(
            Model model
    ) {
        List<Qna> list = qnaService.list();
        model.addAttribute("list", list);
        return "qna/list";
    }

    @GetMapping("/qna/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        Qna qna = qnaService.view(id);
        model.addAttribute("qna", qna);
        return "qna/view";
    }

    @GetMapping("/qna/chuga")
    public String chuga(
            Model model
    ) {
        return "qna/chuga";
    }

    @GetMapping("/qna/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        Qna qna = qnaService.view(id);
        model.addAttribute("qna", qna);
        return "qna/sujung";
    }

    @GetMapping("/qna/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        Qna qna = qnaService.view(id);
        model.addAttribute("qna", qna);
        return "qna/sakje";
    }

    @PostMapping("/qna/chugaProc")
    public String chugaProc(
            QnaDTO qnaDTO
    ) {
        qnaService.chugaProc(qnaDTO);
        return "redirect:/qna/list";
    }

    @PostMapping("/qna/sujungProc")
    public String sujungProc(
            QnaDTO qnaDTO
    ) {
        qnaService.sujungProc(qnaDTO);
        return "redirect:/qna/view/" + qnaDTO.getId();
    }

    @PostMapping("/qna/sakjeProc")
    public String sakjeProc(
            QnaDTO qnaDTO
    ) {
        qnaService.sakjeProc(qnaDTO);
        return "redirect:/qna/list";
    }

}
