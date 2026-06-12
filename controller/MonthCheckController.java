package com.example.masil.controller;


import com.example.masil.dto.MonthCheckDTO;
import com.example.masil.entity.MonthCheck;
import com.example.masil.entity.SiteUser;
import com.example.masil.service.MonthCheckService;
import com.example.masil.service.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MonthCheckController {
    private final MonthCheckService monthCheckService;
    private final SiteUserService siteUserService;

//    @GetMapping("/information/attendance") // 기존 /list에서 /attendance로 변경하여 충돌 방지
//    public String list(Model model, Principal principal){
//        if (principal == null) return "redirect:/user/login";
//
//        monthCheckService.chugaProc(principal.getName()); // 접속 시 자동 출석
//        SiteUser siteUser = siteUserService.getUser(principal.getName());
//
//        List<MonthCheck> monthlist = monthCheckService.list(siteUser);
//
//        // HTML에서 사용할 리스트 이름
//        model.addAttribute("monthlist", monthlist);
//        return "information/list"; // HTML 파일 경로는 그대로 유지
//    }

//    @GetMapping("/monthCheck/view/{id}")
//    public String view(Model model, @PathVariable("id") Long id){
//        MonthCheck monthCheck = monthCheckService.view(id);
//        model.addAttribute("monthCheck", monthCheck);
//        return "monthCheck/view";
//    }

    @PostMapping("/information/chugaProc")
    public String chugaProc(Principal principal) {
        monthCheckService.chugaProc(principal.getName());
        return "redirect:/information/list";
    }

    @PostMapping("/information/sujungProc")
    public String sujungProc(@RequestParam("id") Long id,
                             @RequestParam("newDate") LocalDateTime newDate) {
        monthCheckService.sujungProc(id, newDate);
        return "redirect:/information/list";}

    @GetMapping("/information/sakjeProc/{id}")
    public String sakjeProc(@PathVariable("id") Long id) {
        monthCheckService.sakjeProc(id);
        return "redirect:/information/list";
    }




//@GetMapping("/siteUser/login") // ← 이 주소랑 리다이렉트 주소가 토씨 하나 안 틀리고 같아야 합니다!
//public String login() {
//    return "siteUser/login"; // ← 실제 html 파일 위치 (templates/siteUser/login.html)
//}
}