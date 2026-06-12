package com.example.masil.controller;

import com.example.masil.entity.Content;
import com.example.masil.entity.Quiz;
import com.example.masil.entity.QuizBox;
import com.example.masil.entity.SiteUser;
import com.example.masil.repository.QuizBoxRepository;
import com.example.masil.repository.SiteUserRepository;
import com.example.masil.service.ContentService;
import com.example.masil.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz")
@PreAuthorize("isAuthenticated()") // 로그인된 유저만 진입
public class QuizController {

    private final QuizService quizService;
    private final ContentService contentService;
    private final SiteUserRepository siteUserRepository;
    private final QuizBoxRepository quizBoxRepository;

    // 1. 퀴즈 등록 폼 열기 (강사가 특정 영상에 퀴즈를 새로 심을 때)
    // 주소: /quiz/chuga/{contentId}
    @GetMapping("/chuga/{contentId}")
    public String chugaForm(@PathVariable("contentId") Long contentId, Model model) {
        Content content = contentService.view(contentId);
        if (content == null) {
            return "redirect:/";
        }
        model.addAttribute("content", content);
        model.addAttribute("quiz", new Quiz());
        return "quiz/chuga"; // templates/quiz/chuga.html
    }

    // 2. 퀴즈 저장 처리 로직 (POST)
    // 주소: /quiz/chugaProc
    @PostMapping("/chugaProc")
    public String chugaProc(@ModelAttribute Quiz quiz, @RequestParam("contentId") Long contentId) {
        // 폼에서 받아온 강의 고유 번호를 lectureId에 매핑합니다.
        quiz.setLectureId(contentId);
        quiz.setCreateDate(java.time.LocalDateTime.now());

        quizService.save(quiz);

        // 저장이 끝나면 퀴즈를 등록한 그 강의 시청 화면으로 리다이렉트합니다.
        return "redirect:/quiz/view/" + contentId;
    }

    // 3. 퀴즈 수정 폼 열기
    // 주소: /quiz/sujung/{id}
    @GetMapping("/sujung/{id}")
    public String sujungForm(@PathVariable("id") Long id, Model model) {
        Quiz quiz = quizService.getQuiz(id);
        model.addAttribute("quiz", quiz);
        return "quiz/sujung"; // templates/quiz/sujung.html
    }

    // 4. 퀴즈 수정 처리 로직 (POST)
    // 주소: /quiz/sujungProc
    @PostMapping("/sujungProc")
    public String sujungProc(@ModelAttribute Quiz quiz) {
        quizService.save(quiz);
        // 수정이 끝나면 다시 해당 강의 영상 화면으로 리다이렉트
        return "redirect:/quiz/view/" + quiz.getLectureId();
    }

    // 5. 퀴즈 삭제 처리 로직 (POST)
    // 주소: /quiz/sakjeProc
    @PostMapping("/sakjeProc")
    public String sakjeProc(@RequestParam("id") Long id, @RequestParam("contentId") Long contentId) {
        quizService.delete(id);
        return "redirect:/quiz/view/" + contentId;
    }

    @GetMapping("/view/{lectureId}")
    public String view(@PathVariable("lectureId") Long lectureId, Model model, Principal principal) {
        Content content = contentService.view(lectureId);
        if (content == null) {
            return "redirect:/";
        }
        model.addAttribute("content", content);

        // 1. 유저 정보 가져오기 및 QuizBox 조회
        SiteUser currentUser = null; // 💡 변수 선언
        if (principal != null) {
            currentUser = siteUserRepository.findByUsername(principal.getName()).orElse(null);

            // 해당 카테고리의 QuizBox 조회
            if (currentUser != null) {
                Optional<QuizBox> quizBox = quizBoxRepository.findBySiteUserAndCategoryId(currentUser, content.getCategory().getId());
                // quizBox가 존재하고 stamp가 true면 모델에 넘김
                quizBox.ifPresent(box -> {
                    if (box.isStamp()) { // 💡 stamp가 true인지 확인
                        model.addAttribute("quizBox", box);
                    }
                });
            }
        }

        // 2. 서비스의 list 메서드에 currentUser 전달
        List<Content> contentList = contentService.list(content.getCategory().getId(), currentUser);
        model.addAttribute("contentList", contentList);

        // 3. 퀴즈 정보 로딩
        List<Quiz> quizList = quizService.getQuizByLectureId(lectureId);
        model.addAttribute("quizList", quizList);

        return "content/view";
    }
    @GetMapping("/list")
    public String list(Model model) {
        List<Quiz> quizList = quizService.getList();

        // null이 아닌 데이터만 필터링하여 넘김
        List<Quiz> filteredList = (quizList != null) ?
                quizList.stream().filter(q -> q != null).collect(Collectors.toList()) :
                new ArrayList<>();

        model.addAttribute("quizList", filteredList);
        return "quiz/list";
    }
}