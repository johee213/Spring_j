package com.example.lms.service;

import com.example.lms.dto.LessonDTO;
import com.example.lms.entity.Course;
import com.example.lms.entity.Lesson;
import com.example.lms.entity.Progress;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.LessonRepository;
import com.example.lms.repository.ProgressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ProgressRepository progressRepository;

//    private final String uploadPath = "/Users/macmini/Desktop/lms/videos/";
    private final String uploadPath = "C:/meta12/lms/videos/";

    //@Value("${video.storage.path}")
    //private String uploadPath;


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

        // <----------------------------------------------------------------------------- 추가 시작.
        // 파일 처리
        if (lessonDTO.getVideoFile() != null && !lessonDTO.getVideoFile().isEmpty()) {
            String originalFilename = lessonDTO.getVideoFile().getOriginalFilename();
            // 파일명 중복 방지를 위해 현재 시간 등을 붙일 수 있습니다.
            String saveFileName = System.currentTimeMillis() + "_" + originalFilename;

            try {
                File saveFile = new File(uploadPath + saveFileName);
                lessonDTO.getVideoFile().transferTo(saveFile); // 파일 실제 저장
                lessonDTO.setVideoUrl(saveFileName);           // DB에 저장할 파일명 설정
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // <----------------------------------------------------------------------------- 추가 종료


        // 첨부 파일 처리 추가 시작.
        if (lessonDTO.getAttachFile() != null && !lessonDTO.getAttachFile().isEmpty()) {
            String originalFilename = lessonDTO.getAttachFile().getOriginalFilename();
            String saveFileName = "FILE_" + System.currentTimeMillis() + "_" + originalFilename;

            try {
                File saveFile = new File(uploadPath + saveFileName);
                lessonDTO.getAttachFile().transferTo(saveFile);
                lessonDTO.setFileName(saveFileName);

                // 엔티티에 파일명 정보 세팅 (Lesson.createEntity 메서드 수정 필요)
                //lesson.setFileName(lessonDTO.getAttachFile().getName());               // <---------------- Lesson 엔티티에 추가.
                //lesson.setFileOrigin(lessonDTO.getAttachFile().getOriginalFilename()); // <---------------- Lesson 엔티티에 추가.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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

        lesson.setFileName(lessonDTO.getFileName());

        if (lessonDTO.getAttachFile() == null || lessonDTO.getAttachFile().isEmpty()) {

        } else {
            lesson.setFileOrigin(lessonDTO.getAttachFile().getOriginalFilename());
        }

        return lesson;
    }

    @Transactional
    public void completeLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("차시 없음"));

        Progress progress = new Progress();
        progress.setLesson(lesson);
        progress.setCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());

        progressRepository.save(progress);
    }

}
