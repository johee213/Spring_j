package com.example.masil.config;

import com.example.masil.entity.Category;
import com.example.masil.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringToCategoryConverter implements Converter<String, Category> {

    private final CategoryRepository categoryRepository;

    @Override
    public Category convert(String value) {
        // 숫자(ID)로 들어오면 ID로 조회
        try {
            Long id = Long.parseLong(value);
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("카테고리 없음"));
        } catch (NumberFormatException e) {
            // 문자열(제목)로 들어오면 제목으로 조회
            return categoryRepository.findByTitle(value)
                    .orElseThrow(() -> new RuntimeException("카테고리 없음: " + value));
        }
    }
}