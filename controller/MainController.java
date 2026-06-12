package com.example.masil.controller;

import com.example.masil.entity.OrderPay;
import com.example.masil.entity.SiteUser;
import com.example.masil.entity.Stamp;
import com.example.masil.service.OrderPayService;
import com.example.masil.service.SiteUserService;
import com.example.masil.service.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
private final SiteUserService siteUserService;
private  final OrderPayService orderPayService;
private final StampService stampService;
//    @GetMapping({"", "/"})
//    public String main() {
//        return "main/main"; // 여기서 리턴하는 이름과 HTML 파일명이 똑같아야 합니다!
//    }

    @GetMapping("/")
    public String mainPage(Model model, Principal principal) {
        if (principal != null) {
            SiteUser loginUser = siteUserService.getUser(principal.getName());
            model.addAttribute("loginUser", loginUser);

            boolean isAdmin = (loginUser.getRole() != null && "ADMIN".equals(loginUser.getRole().name()));
            model.addAttribute("isAdmin", isAdmin);

            // 🌟 수정된 부분: stampList가 null인 경우를 방지합니다.
            int stampCount = loginUser.getStampCount();
            List<Stamp> stampList = stampService.findBySiteUser(loginUser);

            // 데이터가 없으면 빈 리스트를 넣어주어 NullPointerException 방지
            model.addAttribute("stampCount", stampCount);
            model.addAttribute("stampList", (stampList != null) ? stampList : new ArrayList<>());
        }

        return "main/main";
    }
}