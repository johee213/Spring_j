package com.example.lms.controller;
import org.springframework.core.io.Resource;
import java.net.MalformedURLException;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriUtils;
import java.nio.charset.StandardCharsets;
import com.example.lms.dto.LessonDTO;
import com.example.lms.entity.Course;
import com.example.lms.entity.Lesson;
import com.example.lms.service.CourseService;
import com.example.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class LessonController {
    private final LessonService lessonService;
    private final CourseService courseService;

    @GetMapping("/lesson/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        Lesson lesson = lessonService.view(id);
        List<Lesson> lessonList = lessonService.list(lesson.getCourse().getId());
        model.addAttribute("lesson", lesson);
        model.addAttribute("lessonList", lessonList);
        return "lesson/view";
    }

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

    @GetMapping("/lesson/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        Lesson lesson = lessonService.view(id);
        if (lesson == null) {
            return "redirect:/";
        }

        model.addAttribute("lesson", lesson);
        return "lesson/sujung";
    }

    @PostMapping("/lesson/sujungProc")
    public String sujungProc(
            LessonDTO lessonDTO
    ) {
        Lesson lesson = lessonService.view(lessonDTO.getId());
        if (lesson == null) {
            return "redirect:/";
        }

        Course course = courseService.view(lessonDTO.getCourseId());
        if (course == null) {
            return "redirect:/";
        }

        lessonService.sujungProc(lessonDTO);
        return "redirect:/course/view/" + lessonDTO.getCourseId();
    }

    @GetMapping("/lesson/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        Lesson lesson = lessonService.view(id);
        if (lesson == null) {
            return "redirect:/";
        }

        model.addAttribute("lesson", lesson);
        return "lesson/sakje";
    }

    @PostMapping("/lesson/sakjeProc")
    public String sakjeProc(
            LessonDTO lessonDTO
    ) {
        Lesson lesson = lessonService.view(lessonDTO.getId());
        if (lesson == null) {
            return "redirect:/";
        }

        Course course = courseService.view(lessonDTO.getCourseId());
        if (course == null) {
            return "redirect:/";
        }

        lessonService.sakjeProc(lessonDTO);
        return "redirect:/course/view/" + lessonDTO.getCourseId();
    }

    @PostMapping("/lesson/complete/{id}")
    public String complete(
            @PathVariable("id") Long id
    ){
        Lesson lesson = lessonService.view(id);
        if(lesson == null) {
            return  "redirect:/";
        }
        lessonService.completeLesson(id);
        return "ok";
    }


    @GetMapping("lesson/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws MalformedURLException {


        Lesson lesson = lessonService.view(id);
        String fullPath = "C:/meta12/lms/videos/" + lesson.getFileName();
        UrlResource resource = new UrlResource("file:" + fullPath);

        // 한글 파일명 깨짐 방지 설정
        String encodedUploadFileName = UriUtils.encode(lesson.getFileOrigin(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
