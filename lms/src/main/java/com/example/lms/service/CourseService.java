package com.example.lms.service;

import com.example.lms.dto.CourseDTO;
import com.example.lms.entity.Course;
import com.example.lms.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Course view(Long id) {
        Course course = null;
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            course = optionalCourse.get();
        }
        return course;
    }

    public void chugaProc(CourseDTO courseDTO) {
        Course course = createEntity(courseDTO);
        courseRepository.save(course);
    }

    public void sujungProc(CourseDTO courseDTO) {
        Course course = createEntity(courseDTO);
        courseRepository.save(course);
    }

    public void sakjeProc(CourseDTO courseDTO) {
        Course course = createEntity(courseDTO);
        courseRepository.delete(course);
    }

    public Course createEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setTitle(courseDTO.getTitle());
        course.setInstructor(courseDTO.getInstructor());
        course.setDescription(courseDTO.getDescription());
        return course;
    }
}
