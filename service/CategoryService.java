package com.example.masil.service;

import com.example.masil.dto.CategoryDTO;
import com.example.masil.entity.Category;
import com.example.masil.entity.Content;
import com.example.masil.entity.Progress;
import com.example.masil.entity.SiteUser;
import com.example.masil.repository.CategoryRepository;
import com.example.masil.repository.ContentRepository;
import com.example.masil.repository.ProgressRepository;
import com.example.masil.repository.OrderPayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ContentRepository contentRepository;
    private final ProgressRepository progressRepository;
    private final OrderPayRepository orderPayRepository;

    private final String uploadPath = "C:/meta12/masil/category_images/";

    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    public List<Content> list(Long categoryId, SiteUser user) {
        List<Content> contentList = contentRepository.findByCategoryIdOrderBySequenceAsc(categoryId);
        for (Content content : contentList) {
            if (user != null) {
                Optional<Progress> progress = progressRepository.findBySiteUserAndContent(user, content);
                if (progress.isPresent()) {
                    // double 타입인 percentage를 int로 올바르게 강제 형변환(Casting)
                    int val = (int) Math.round(progress.get().getPercentage());
                    content.setProgressPercent(val);
                } else {
                    content.setProgressPercent(0);
                }
            } else {
                content.setProgressPercent(0);
            }
        }
        return contentList;
    }

    public Category view(Long id){
        Category category = null;
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            category = optionalCategory.get();
        }
        return category;
    }

    public void chugaProc(CategoryDTO categoryDTO){

        if (categoryDTO.getAttachFile() != null && !categoryDTO.getAttachFile().isEmpty()){
            String originalFilename = categoryDTO.getAttachFile().getOriginalFilename();
            String saveFileName = "FILE_" + System.currentTimeMillis() + "_" + originalFilename;

            try{
                File saveFile = new File(uploadPath + saveFileName);
                categoryDTO.getAttachFile().transferTo(saveFile);
                categoryDTO.setFileName(saveFileName);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        Category category = createEntity(categoryDTO);
         categoryRepository.save(category);


    }

    public void sujungProc(CategoryDTO categoryDTO){
        Category category = createEntity(categoryDTO);
        categoryRepository.save(category);
    }

    @Transactional
    public void sakjeProc(CategoryDTO categoryDTO) {
        orderPayRepository.deleteByCategoryId(categoryDTO.getId());
        Category category = createEntity(categoryDTO);
        categoryRepository.delete(category);
    }

    public Category createEntity(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setTitle(categoryDTO.getTitle());
        category.setInstructor(categoryDTO.getInstructor());
        category.setDescription(categoryDTO.getDescription());
        category.setFileName(categoryDTO.getFileName());

        if (categoryDTO.getAttachFile() == null || categoryDTO.getAttachFile().isEmpty()){

        }else{
            category.setFileOrigin(categoryDTO.getAttachFile().getOriginalFilename());
        }
        return category;
    }

    @Transactional
    public void delete(Long id) {
        orderPayRepository.deleteByCategoryId(id);
        categoryRepository.deleteById(id);
    }

    @Transactional
    public void deleteMultiple(List<Long> ids) {
        for (Long id : ids) {
            if (categoryRepository.existsById(id)) {
                // order_pay 먼저 삭제
                orderPayRepository.deleteByCategoryId(id);
                categoryRepository.deleteById(id);
            }
        }
    }
}
