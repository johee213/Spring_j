package com.example.masil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;       //강좌제목
    private String instructor;  //강사명
    private String description; //강좌설명

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Content> contents;

    private String fileName;
    private String fileOrigin;

    @OneToMany(mappedBy = "category")
    @JsonIgnore // 💡 이 어노테이션을 추가하세요!
    private List<Content> contentList;
}