package com.example.blog.controller;



import com.example.blog.dto.QuestionDTO;
import com.example.blog.entity.Question;
import com.example.blog.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(
            Model model){
        List<Question> list = questionService.list();
        model.addAttribute("list", list);
        return "question/list";
    }


    @GetMapping("/question/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id)
    {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
        Question question = questionService.view(questionDTO);
//        if(question == null) {
////            return  "redirect:/";
//        }
        model.addAttribute("question", question);
        return "question/view";

    }
    @GetMapping("/question/chuga")
    public String chuga(Model model){
    return "question/chuga";
    }


    @GetMapping("/question/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
        Question question = questionService.view(questionDTO);
//        if(question == null) {
//            return  "redirect:/";
//        }
        model.addAttribute("question", question);
        return "question/sujung";

    }

    @GetMapping("/question/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
        Question question = questionService.view(questionDTO);
//        if(question == null) {
//            return  "redirect:/";
//        }
        model.addAttribute("question", question);
        return "question/sakje";

    }
    @PostMapping("/question/chugaProc")
    public String chugaProc( QuestionDTO questionDTO
    ){
        questionService.chugaProc(questionDTO);
        return "redirect:/question/view";
    }




    @PostMapping("/question/sujungProc")
    public String sujungProc( QuestionDTO questionDTO
    ){
        if(questionDTO == null) {
            return "redirect:/";
        }
        questionService.sakjeProc(questionDTO);
        return "redirect:/qestion/view";
    }






    @PostMapping("/question/sakjeProc")
    public String sakjeProc(
            QuestionDTO questionDTO
    ){
//        if(questionDTO == null) {
//            return "redirect:/";
//        }
        questionService.sakjeProc(questionDTO);
        return "redirect:/list";
    }

    }





















