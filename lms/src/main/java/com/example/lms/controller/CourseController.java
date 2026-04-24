package com.example.lms.controller;

import com.example.lms.dto.CourseDTO;
import com.example.lms.entity.Course;
import com.example.lms.entity.Lesson;
import com.example.lms.service.CourseService;
import com.example.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController {
    private final CourseService courseService;
    private final LessonService lessonService;

    @GetMapping("/course/list")
    public String list(
            Model model
    ) {
        List<Course> list = courseService.list();
        model.addAttribute("list", list);
        return "course/list";
    }

    @GetMapping("/course/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        Course course = courseService.view(id);
        if (course == null) {
            return "redirect:/";
        }

        List<Lesson> lessonList = lessonService.list(id);

        model.addAttribute("course", course);
        model.addAttribute("lessonList", lessonList); //차시 리스트
        return "course/view";
    }

    @GetMapping("/course/chuga")
    public String chuga() {
        return "course/chuga";
    }

    @GetMapping("/course/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        Course course = courseService.view(id);
        if (course == null) {
            return "redirect:/";
        }
        model.addAttribute("course", course);
        return "course/sujung";
    }

    @GetMapping("/course/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        Course course = courseService.view(id);
        if (course == null) {
            return "redirect:/";
        }
        model.addAttribute("course", course);
        return "course/sakje";
    }

    @PostMapping("/course/chugaProc")
    public String chugaProc(
            CourseDTO courseDTO
    ) {
        courseService.chugaProc(courseDTO);
        return "redirect:/course/list";
    }

    @PostMapping("/course/sujungProc")
    public String sujungProc(
            CourseDTO courseDTO
    ) {
        Course course = courseService.view(courseDTO.getId());
        if (course == null) {
            return "redirect:/";
        }
        courseService.sujungProc(courseDTO);
        return "redirect:/course/view/" + courseDTO.getId();
    }

    @PostMapping("/course/sakjeProc")
    public String sakjeProc(
            CourseDTO courseDTO
    ) {
        Course course = courseService.view(courseDTO.getId());
        if (course == null) {
            return "redirect:/";
        }
        courseService.sakjeProc(courseDTO);
        return "redirect:/course/list";
    }
}
