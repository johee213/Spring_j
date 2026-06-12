package com.example.masil.controller;


import com.example.masil.dto.AnswerDTO;
import com.example.masil.dto.QuestionDTO;
import com.example.masil.entity.Question;
import com.example.masil.entity.SiteUser;
import com.example.masil.service.QuestionService;
import com.example.masil.service.SiteUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final SiteUserService siteUserService;

    @GetMapping("/question/list")
    public String list(
            Model model,
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value="kw", defaultValue="") String kw, // kw 추가
            Principal principal
    ) {
        // 서비스 호출 시 kw 전달
        Page<Question> paging = questionService.list(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw); // 화면에 검색어 유지를 위해 추가

        boolean isAdmin = false;
        if (principal != null) {
            SiteUser loginUser = siteUserService.getUser(principal.getName());
            if (loginUser.getRole() != null && "ADMIN".equals(loginUser.getRole().name())) {
                isAdmin = true;
            }
        }
        model.addAttribute("isAdmin", isAdmin);

        return "question/list";
    }

    @GetMapping("/question/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id,
            AnswerDTO answerDTO,
            Principal principal // ✅ 로그인 정보 확인을 위해 추가
    ) {
        Question question = questionService.view(id);
        model.addAttribute("question", question);

        // 🚀 isAdmin 변수를 추가해서 navbar 에러를 방지합니다.
        boolean isAdmin = false;
        if (principal != null) {
            SiteUser loginUser = siteUserService.getUser(principal.getName());
            if (loginUser.getRole() != null && "ADMIN".equals(loginUser.getRole().name())) {
                isAdmin = true;
            }
        }
        model.addAttribute("isAdmin", isAdmin);

        return "question/view";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/chuga")
    public String chuga(Model model, QuestionDTO questionDTO, Principal principal) {

        // 🚀 isAdmin 변수를 추가해서 내비게이션 바 에러를 방지합니다.
        boolean isAdmin = false;
        if (principal != null) {
            SiteUser loginUser = siteUserService.getUser(principal.getName());
            if (loginUser.getRole() != null && "ADMIN".equals(loginUser.getRole().name())) {
                isAdmin = true;
            }
        }

        model.addAttribute("isAdmin", isAdmin); // 이 줄이 꼭 있어야 합니다!
        return "question/chuga";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/chugaProc")
    public String chugaProc(
            @Valid QuestionDTO questionDTO,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "question/chuga"; //forwarding
        }

        SiteUser siteUser = siteUserService.getUser(principal.getName());
        questionService.chugaProc(questionDTO, siteUser);
        return "redirect:/question/list";
    }

    // [1단계] 수정 화면 보여주기 (GET)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/sujung/{id}")
    // 메서드 선언부 끝에 'HttpServletRequest request'를 추가합니다.
    public String sujung(
            Model model,
            @PathVariable("id") long id,
            QuestionDTO questionDTO,
            Principal principal,
            HttpServletRequest request /* <--- 이 부분이 꼭 필요합니다 */
    ) {
        Question question = questionService.view(id); // 기존 데이터 가져오기

        // 이제 아래의 request.isUserInRole 코드가 정상적으로 작동합니다.
        if(!question.getAuthor().getUsername().equals(principal.getName()) && !request.isUserInRole("ROLE_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionDTO.setId(question.getId());
        questionDTO.setSubject(question.getSubject());
        questionDTO.setContent(question.getContent());

        model.addAttribute("questionDTO", questionDTO);
        return "question/sujung";
    }

    // [2단계] 실제 수정 처리하기 (POST)
    @PreAuthorize("isAuthenticated()")
// 1. 주소를 /question/sujung/{id} 로 HTML과 일치시킵니다.
    @PostMapping("/question/sujung/{id}")
    public String sujungProc(
            @PathVariable("id") long id, // 2. 주소에 {id}가 추가되었으므로 @PathVariable이 필요합니다.
            QuestionDTO questionDTO,
            Principal principal,
            HttpServletRequest request
    ) {
        // 1. 기존 데이터 조회
        Question question = questionService.view(id);

        // 2. 권한 체크
        if(!question.getAuthor().getUsername().equals(principal.getName())
                && !request.isUserInRole("ROLE_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        // 3. 서비스 호출하여 DB에 저장
        questionService.modify(question, questionDTO.getSubject(), questionDTO.getContent());

        // 4. 상세 페이지(view)로 돌아가기
        // 상세 페이지 주소가 /question/view/{id} 라면 아래처럼 적어주세요.
        return "redirect:/question/view/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/sakje/{id}")
    public String sakje(
            Principal principal,
            @PathVariable("id") long id,
            HttpServletRequest request,
            Model model  // ★ Model 추가
    ) {
        // 1. 기존 엔티티 조회 (권한 확인 및 화면 표시용)
        Question question = this.questionService.view(id);

        // 2. 권한 체크 (본인 또는 관리자)
        if (!question.getAuthor().getUsername().equals(principal.getName())
                && !request.isUserInRole("ROLE_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        // 3. [중요] 템플릿에 데이터 전달
        // 이 부분이 있어야 HTML의 ${question.subject}가 작동합니다.
        model.addAttribute("question", question);

        // 4. 아직 삭제(sakjeProc)를 호출하지 않고, 확인 페이지(HTML)만 보여줍니다.
        return "question/sakje";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/sakjeProc")
    public String sakjeProc(
            //@Valid QuestionDTO questionDTO,
            //BindingResult bindingResult,
            @RequestParam("id") Long id,
            Principal principal
    ) {
        Question question = questionService.view(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);

        questionService.sakjeProc(questionDTO, question.getAuthor());
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/mylist")
    public String mylist(
            Model model,
            @RequestParam(value="page", defaultValue="0") int page,
            Principal principal
    ) {
        // 1. 현재 로그인한 사용자 정보 가져오기
        SiteUser siteUser = this.siteUserService.getUser(principal.getName());

        // 2. 해당 사용자의 문의 내역만 조회
        Page<Question> paging = this.questionService.getMyList(page, siteUser);

        model.addAttribute("paging", paging);

        // 내비게이션 바 에러 방지를 위한 관리자 체크 (기존 로직 유지)
        boolean isAdmin = siteUser.getRole() != null && "ADMIN".equals(siteUser.getRole().name());
        model.addAttribute("isAdmin", isAdmin);

        return "question/mylist"; // 새로 만들 HTML 파일명
    }


}
