package com.example.remi.controller;

import com.example.remi.dto.StudentGradeDTO;
import com.example.remi.entity.StudentGrade;
import com.example.remi.service.StudentGradeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/studentGrade")
@RequiredArgsConstructor
@Controller
public class StudentGradeController {
    private final StudentGradeService studentGradeService;

    @GetMapping("/list")
    public String list(
            Model model
    ) {
        List<StudentGrade> list = studentGradeService.list();
        System.out.println("list : " + list.size());
        model.addAttribute("list", list);
        return "studentGrade/list";
    }

    @GetMapping("/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        StudentGrade studentGrade = studentGradeService.view(id);
        model.addAttribute("studentGrade", studentGrade);
        return "studentGrade/view";
    }

    @GetMapping("/chuga")
    public String chuga(
            Model model
    ) {
        return "studentGrade/chuga";
    }

    @GetMapping("/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        StudentGrade studentGrade = studentGradeService.view(id);
        model.addAttribute("studentGrade", studentGrade);
        return "studentGrade/sujung";
    }

    @GetMapping("/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        StudentGrade studentGrade = studentGradeService.view(id);
        model.addAttribute("studentGrade", studentGrade);
        return "studentGrade/sakje";
    }

    @PostMapping("/chugaProc")
    public String chugaProc(
            StudentGradeDTO studentGradeDTO
    ) {
        System.out.println("111");
        studentGradeService.chugaProc(studentGradeDTO);
        System.out.println("222");
        return "redirect:/studentGrade/list";
    }

    @PostMapping("/sujungProc")
    public String sujungProc(
            StudentGradeDTO studentGradeDTO
    ) {
        studentGradeService.sujungProc(studentGradeDTO);
        return "redirect:/studentGrade/view/" +  studentGradeDTO.getId();
    }

    @PostMapping("/sakjeProc")
    public String sakjeProc(
            StudentGradeDTO studentGradeDTO
    ) {
        studentGradeService.sakjeProc(studentGradeDTO);
        return "redirect:/studentGrade/list";
    }
}
