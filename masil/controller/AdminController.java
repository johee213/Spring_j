package com.example.masil.controller;

import com.example.masil.service.SiteUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin") // 모든 경로는 /admin으로 시작합니다.
public class AdminController {

    // 1. 서비스 선언 (클래스 최상단에 위치해야 합니다)
    private final SiteUserService siteUserService;

    // 2. 생성자 주입 (빨간 줄을 없애주는 핵심 코드입니다)
    public AdminController(SiteUserService siteUserService) {
        this.siteUserService = siteUserService;
    }

    // [기존 페이지] http://localhost:8087/admin/main
    @GetMapping("/main")
    public String adminMain(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("totalUsers", "128명");
        model.addAttribute("waitingQna", "5건");
        model.addAttribute("activeCourses", "12개");
        model.addAttribute("todayNews", "2건");
        model.addAttribute("categoryData", List.of(40, 25, 20, 15));

        model.addAttribute("menu", "main"); // 현재 메뉴 식별자 추가
        return "admin/admin_main";
    }

    // [연령대 차트 페이지] http://localhost:8087/admin/members
    @GetMapping("/members")
    public String adminMembers(Model model, HttpServletRequest request) {
        // 현재 접속 주소 정보를 모델에 담습니다.
        model.addAttribute("currentUri", request.getRequestURI());

        // 서비스에서 수강생 연령대 통계 데이터를 가져옵니다.
        Map<String, Object> ageData = siteUserService.getAgeChartData();

        // 차트에 필요한 라벨과 수치 데이터를 모델에 담습니다.
        model.addAttribute("ageLabels", ageData.get("labels"));
        model.addAttribute("ageData", ageData.get("data"));

        // 관리자 멤버 관리 페이지(admin/members.html)를 반환합니다.
        return "admin/members";
    }
}