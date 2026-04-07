package com.meta12.yuni01.entity;

import com.meta12.yuni01.dto.GuestBookDTO;
import com.meta12.yuni01.dto.QnaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Table(name = "guestBook")
@Getter
@Setter
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", insertable = true, updatable = false, length = 50, nullable = false)
    private String name;
    private String subject;
    private String content;

    public static Qna dtoToEntity(QnaDTO qnaDTO) {
        Qna qna = new Qna();
        qna.setId(qnaDTO.getId());
        qna.setName(qnaDTO.getName());
        qna.setSubject(qnaDTO.getSubject());
        qna.setContent(qnaDTO.getContent());
        return qna;
    }
}
