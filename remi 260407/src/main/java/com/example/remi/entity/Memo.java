package com.example.remi.entity;

import com.example.remi.dto.MemoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity // <---
@Getter // <---
@Setter // <---
@Table(name = "memo")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "writer", insertable = true, updatable = false, length = 50, nullable = false)
    private String writer;

    private String content;


    public static Memo createEntity(MemoDTO memoDTO) {
        Memo memo = new Memo();
        memo.setId(memoDTO.getId());
        memo.setWriter(memoDTO.getWriter());
        memo.setContent(memoDTO.getContent());
        return memo;
    }
}
