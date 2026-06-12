package com.example.masil.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class CategoryDTO {
 private Long id;
 private String title;
 private String instructor;
 private String description;

 private MultipartFile attachFile;
 private String fileName;
 private String fileOrigin;
}
