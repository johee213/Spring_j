package com.example.lms.controller;

import com.example.lms.dto.LessonDTO;
import com.example.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/lesson/chuga/{courseId}")
    public String chuga(
            Model model,
            @PathVariable("courseId") Long courseId
    ) {
//        LessonDTO lessonDTO = new LessonDTO();
//        lessonDTO.setCourseId(courseId);
//        model.addAttribute("lessonDTO", lessonDTO);
        model.addAttribute("courseId", courseId);
        return "lesson/chuga";
    }

    @PostMapping("/lesson/chugaProc")
    public String chugaProc(
            LessonDTO lessonDTO
    ) {
        lessonService.chugaProc(lessonDTO);
        return "redirect:/course/view/" + lessonDTO.getCourseId();
    }
}
