package com.example.lms.service;

import com.example.lms.dto.LessonDTO;
import com.example.lms.entity.Course;
import com.example.lms.entity.Lesson;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public List<Lesson> list(Long courseId) {
        return lessonRepository.findByCourseIdOrderBySequenceAsc(courseId);
    }

    public Lesson view(Long id) {
        Lesson lesson = null;
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if (optionalLesson.isPresent()) {
            lesson = optionalLesson.get();
        }
        return lesson;
    }

    public void chugaProc(LessonDTO lessonDTO) {
        //해당 강의가 있는지 없는지 확인.
        Course course = courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다."));

        Lesson lesson = createEntity(lessonDTO, course);
        lessonRepository.save(lesson);
    }

    public void sujungProc(LessonDTO lessonDTO) {
        //해당 강의가 있는지 없는지 확인.
        Course course = courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다."));

        Lesson lesson = createEntity(lessonDTO, course);
        lessonRepository.save(lesson);
    }

    public void sakjeProc(LessonDTO lessonDTO) {
        //해당 강의가 있는지 없는지 확인.
        Course course = courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다."));

        Lesson lesson = createEntity(lessonDTO, course);
        lessonRepository.delete(lesson);
    }

    public Lesson createEntity(LessonDTO lessonDTO, Course course) {
        Lesson lesson = new Lesson();
        lesson.setId(lessonDTO.getId());
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setVideoUrl(lessonDTO.getVideoUrl());
        lesson.setSequence(lessonDTO.getSequence());
        lesson.setCourse(course);

        return lesson;
    }

}
