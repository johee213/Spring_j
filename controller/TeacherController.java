package com.example.masil.controller;


import com.example.masil.dto.TeacherDTO;
import com.example.masil.entity.Category;
import com.example.masil.entity.OrderPay;
import com.example.masil.entity.SiteUser;
import com.example.masil.entity.Teacher;
import com.example.masil.repository.CategoryRepository;
import com.example.masil.repository.OrderPayRepository;
import com.example.masil.repository.SiteUserRepository;
import com.example.masil.service.OrderPayService;
import com.example.masil.service.SiteUserService;
import com.example.masil.service.StampService;
import com.example.masil.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/teacher")
@RequiredArgsConstructor
@Controller
public class TeacherController {
    private final TeacherService teacherService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SiteUserRepository siteUserRepository;
    private final OrderPayRepository orderPayRepository;
    private final SiteUserService siteUserService;
    private final CategoryRepository categoryRepository;
    private final StampService stampService;
    private final OrderPayService orderPayService;

    // [수정] 검색 기능 적용: category와 teacherName을 파라미터로 받음
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "category", required = false) String category,
                       @RequestParam(value = "teacherName", required = false) String teacherName) {

        // 서비스의 오버로딩된 list(String, String) 메서드 호출 (빨간 줄 해결!)
        List<Teacher> list = teacherService.list(category, teacherName);

        model.addAttribute("list", list);
        model.addAttribute("category", category);    // 검색 후에도 선택값 유지
        model.addAttribute("teacherName", teacherName); // 검색 후에도 입력값 유지

        return "teacher/list";
    }

    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable("id") Long id) {
        Teacher teacher = teacherService.view(id);
        if (teacher == null) return "redirect:/teacher/list";
        model.addAttribute("teacher", teacher);
        return "teacher/view";
    }

    @GetMapping("/chuga")
    public String chuga(Model model) {
        model.addAttribute("instructorForm", new TeacherDTO());
        return "teacher/chuga";
    }

    @GetMapping("/sujung/{id}")
    public String sujungForm(@PathVariable("id") Long id, Model model) {
        Teacher teacher = teacherService.view(id);
        model.addAttribute("teacher", teacher);
        return "teacher/sujung";
    }

    @GetMapping("/sakje/{id}")
    public String sakje(Model model, @PathVariable("id") Long id) {
        Teacher teacher = teacherService.view(id);
        if (teacher == null) return "redirect:/teacher/list";
        model.addAttribute("teacher", teacher);
        return "teacher/sakje";
    }

    @PostMapping("/chugaProc")
    public String chugaProc(@ModelAttribute TeacherDTO teacherDTO, RedirectAttributes redirectAttributes) {
        try {
            teacherService.chugaProc(teacherDTO);
            redirectAttributes.addFlashAttribute("message", "새로운 강사가 등록되었습니다! 🍊");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "등록 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/teacher/list";
    }

    @PostMapping("/sujungProc")
    public String sujungProc(@ModelAttribute TeacherDTO teacherDTO, RedirectAttributes redirectAttributes) {
        Teacher teacher = teacherService.view(teacherDTO.getId());

        // [수정] 암호화된 비밀번호와 입력값을 비교하도록 변경
        if (teacher == null || !passwordEncoder.matches(teacherDTO.getPassword(), teacher.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:/teacher/sujung/" + teacherDTO.getId();
        }

        teacherService.sujungProc(teacherDTO);
        return "redirect:/teacher/view/" + teacherDTO.getId();
    }

    @PostMapping("/sakjeProc")
    public String sakjeProc(@ModelAttribute TeacherDTO teacherDTO, RedirectAttributes redirectAttributes) {
        Teacher teacher = teacherService.view(teacherDTO.getId());

        if (teacher == null || !teacher.getPassword().equals(teacherDTO.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않아 삭제할 수 없습니다.");
            return "redirect:/teacher/view/" + teacherDTO.getId();
        }

        teacherService.sakjeProc(teacherDTO);
        return "redirect:/teacher/list";
    }

    @GetMapping("/main")
    public String instructorMain(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/siteUser/login";
        }

        // 1. 현재 로그인한 강사 정보 조회
        Teacher currentTeacher = teacherService.findByLoginId(principal.getName());

        // 2. 학생 명단 가져오기 (OrderPay를 뒤지지 말고 서비스에서 Role.USER인 학생들만 가져옴)
        List<SiteUser> studentList = teacherService.getStudentList();

        // 3. 모델에 데이터 추가
        if (currentTeacher != null) {
            model.addAttribute("currentCategory", currentTeacher.getCategory());
        }
        model.addAttribute("studentList", studentList);

        return "teacher/main";
    }

    // [추가할 코드] 👍 참 잘했어요 도장 비동기(AJAX) API
    // HTML 내부 스크립트가 /instructor/give-stamp 주소로 POST 요청을 보내므로 이곳에 구현합니다.
    // TeacherController.java (또는 관련 서비스) 내부 도장 지급 로직 예시
    // 🔥 [핵심 1] 비동기 POST 요청을 처리하고, 결과를 JSON(숫자)으로 리턴하기 위한 어노테이션 필수!
    @PostMapping("/give-stamp")
    @ResponseBody
    public ResponseEntity<Integer> giveStamp(@RequestParam("studentId") Long studentId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 1. 도장 지급 서비스 호출 (StampService 사용)
        // 💡 StampService.giveStamp 안에서 student의 stampCount를 +1 하도록 만드세요.
        stampService.giveStamp(studentId, principal.getName());

        // 2. 해당 학생의 최신 도장 개수만 DB에서 조회
        SiteUser student = siteUserService.getUserById(studentId);

        // 💡 이제는 OrderPay를 뒤지지 말고, SiteUser의 stampCount를 바로 리턴합니다.
        return ResponseEntity.ok(student.getStampCount());
    }
}