package com.example.masil.controller;

import com.example.masil.dto.QuizBoxDTO; // ✨ 변경된 DTO 이름 임포트
import com.example.masil.entity.OrderPay;
import com.example.masil.entity.Quiz;
import com.example.masil.entity.SiteUser;
import com.example.masil.service.OrderPayService;
import com.example.masil.service.QuizBoxService;
import com.example.masil.service.QuizService;
import com.example.masil.service.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quizbox")
public class QuizBoxController {

    private final QuizBoxService quizBoxService;
    private final SiteUserService siteUserService;
    private final OrderPayService orderPayService;
    private final QuizService quizService;

    // 3문제 중 2문제 이상 합격 커트라인 체크 컨트롤러
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/checkQuiz")
    public String checkQuiz(@AuthenticationPrincipal UserDetails userDetails,
                            @ModelAttribute QuizBoxDTO quizBoxDto, // ✨ 깔끔하게 DTO로 수신
                            RedirectAttributes redirectAttributes) {

        int correctCount = 0;

        // DTO에서 퀴즈 ID 및 사용자 답안 리스트 추출
        List<Long> quizIds = quizBoxDto.getQuizIds();
        List<String> userAnswers = quizBoxDto.getUserAnswers();

        // 1. 사용자가 푼 3문제를 돌면서 정답을 채점
        for (int i = 0; i < quizIds.size(); i++) {
            Quiz quiz = quizService.getQuiz(quizIds.get(i));
            String dbAnswer = quiz.getAnswer();
            String userAnswer = userAnswers.get(i);

            if (dbAnswer != null && dbAnswer.trim().equalsIgnoreCase(userAnswer.trim())) {
                correctCount++;
            }
        }

        // 2. 2문제 이상 맞혔는지 검사
        if (correctCount >= 2) {
            String username = userDetails.getUsername();
            SiteUser siteUser = siteUserService.getUser(username);

            List<OrderPay> orderPayList = orderPayService.list(username);
            OrderPay orderPay = (orderPayList != null && !orderPayList.isEmpty()) ? orderPayList.get(orderPayList.size() - 1) : null;

            // DTO에서 categoryId, categoryName을 꺼내어 QuizBox 저장
            quizBoxService.saveQuizSuccess(siteUser, orderPay, quizBoxDto.getCategoryId(), quizBoxDto.getCategoryName(), correctCount);

            redirectAttributes.addFlashAttribute("quizResult", "🎉 축하합니다! " + correctCount + "문제를 맞혀 도장이 찍혔습니다!");
        } else {
            redirectAttributes.addFlashAttribute("quizResult", "😢 " + correctCount + "다시 시도해보세요!");
        }

        // 3. 보던 강의 영상 뷰 주소로 리다이렉트하고, 뒤에 #quiz-area를 붙여 해당 스크롤 위치로 고정시킵니다.
        return "redirect:/content/view/" + quizBoxDto.getContentId() + "#quiz-area";
    }
}