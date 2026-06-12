package com.example.masil.controller;

import com.example.masil.entity.Community;
import com.example.masil.entity.SiteUser;
import com.example.masil.repository.SiteUserRepository;
import com.example.masil.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final SiteUserRepository siteUserRepository;

    // 목록 조회
    @GetMapping("/list")
    public String list(Model model,
                       Principal principal,
                       @RequestParam(value = "sort", defaultValue = "desc") String sort) {

        List<Community> commentList;
        if (sort.equals("asc")) {
            commentList = communityService.getListAsc(); // 오래된순
        } else {
            commentList = communityService.getList();    // 최신순(기본)
        }

        model.addAttribute("commentList", commentList);
        model.addAttribute("sort", sort);

        if (principal != null) {
            model.addAttribute("loginUsername", principal.getName());
        }

        return "community/list";
    }

    // 댓글 등록
    @PostMapping("/list")
    public String register(@ModelAttribute Community community, Principal principal) {
        if (principal == null) return "redirect:/user/login";

        if (community.getContent() == null || community.getContent().trim().isEmpty()) {
            return "redirect:/community/list";
        }

        SiteUser user = siteUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        community.setAuthor(user);
        communityService.save(community);

        return "redirect:/community/list";
    }

    // 댓글 수정
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("content") String content,
                         Principal principal) {

        if (content == null || content.trim().isEmpty()) {
            return "redirect:/community/list";
        }

        communityService.modify(id, content, principal.getName());
        return "redirect:/community/list";
    }

    // 댓글 삭제
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Authentication authentication) {
        // 이미 컨트롤러에서 Authentication을 받고 있다면 바로 서비스로 전달
        this.communityService.delete(id, authentication);
        return "redirect:/community/list";
    }

    // 상세 보기
    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable("id") Long id) {
        Community community = communityService.getCommunity(id);
        model.addAttribute("community", community);
        return "community/view";
    }
}