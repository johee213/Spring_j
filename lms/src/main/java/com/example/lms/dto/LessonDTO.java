package com.example.lms.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LessonDTO {
    private Long id;
    private String title;
    private String videoUrl;
    private Integer sequence;

    private Long courseId; // 어느 강의에 속하는지

    private MultipartFile videoFile; // 업로드된 실제 파일 객체 (추가)

    private MultipartFile attachFile; // 첨부 파일 객체
    private String fileName;          // 서버에 저장된 실제 첨부 파일명
}
