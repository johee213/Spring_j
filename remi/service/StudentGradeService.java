package com.example.remi.service;

import com.example.remi.dto.StudentGradeDTO;
import com.example.remi.entity.StudentGrade;
import com.example.remi.repository.StudentGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentGradeService {
    private final StudentGradeRepository studentGradeRepository;

    private StudentGrade createEntity(StudentGradeDTO studentGradeDTO) {
        StudentGrade studentGrade = new StudentGrade();
        studentGrade.setId(studentGradeDTO.getId());
        studentGrade.setName(studentGradeDTO.getName());
        studentGrade.setKorScore(studentGradeDTO.getKorScore());
        studentGrade.setEngScore(studentGradeDTO.getEngScore());
        studentGrade.setMathScore(studentGradeDTO.getMathScore());
        return studentGrade;
    }

    public List<StudentGrade> list() {
        return studentGradeRepository.findAll();
    }

    public StudentGrade view(Long id) {
        Optional<StudentGrade> os = studentGradeRepository.findById(id);
        StudentGrade studentGrade = null;
        if (os.isPresent()) {
            studentGrade = os.get();
        }
        return studentGrade;
    }

    public void chugaProc(StudentGradeDTO studentGradeDTO) {
        StudentGrade studentGrade = createEntity(studentGradeDTO);
        studentGradeRepository.save(studentGrade);
    }

    public void sujungProc(StudentGradeDTO studentGradeDTO) {
        StudentGrade studentGrade = createEntity(studentGradeDTO);
        studentGradeRepository.save(studentGrade);
    }

    public void sakjeProc(StudentGradeDTO studentGradeDTO) {
        StudentGrade studentGrade = createEntity(studentGradeDTO);
        studentGradeRepository.delete(studentGrade);
    }
}
