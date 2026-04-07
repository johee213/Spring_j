package com.meta12.yuni01.service;

import com.meta12.yuni01.dto.GuestBookDTO;
import com.meta12.yuni01.dto.QnaDTO;
import com.meta12.yuni01.entity.GuestBook;
import com.meta12.yuni01.entity.Qna;
import com.meta12.yuni01.repository.GuestBookRepository;
import com.meta12.yuni01.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final QnaRepository qnaRepository;

    public List<Qna> list() {
        return qnaRepository.findAll();
    }

    public Qna view(Long id) {
        Optional<Qna> og = qnaRepository.findById(id);
        Qna qna = null;
        if (og.isPresent()) {
            qna = og.get();
        }
        return qna;
    }

    public void chugaProc(QnaDTO qnaDTO) {
        Qna qna = Qna.dtoToEntity(qnaDTO); // dto -> entity
        qnaRepository.save(qna);
    }

    public void sujungProc(QnaDTO qnaDTO) {
        Qna qna = Qna.dtoToEntity(qnaDTO);
        qnaRepository.save(qna);
    }

    public void sakjeProc(QnaDTO qnaDTO) {
        Qna qna = Qna.dtoToEntity(qnaDTO);
        qnaRepository.delete(qna);
    }

}
