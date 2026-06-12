package com.example.masil.controller;

import com.example.masil.dto.FaqDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/faq") // 주소가 localhost:8080/faq 로 시작합니다.
public class FaqController {

    @GetMapping("/main") // 최종 주소: localhost:8080/faq/main
    public String faqMain() {
        // templates/support/help_center.html 파일을 보여줍니다.
        // (폴더 구조는 아래 2번을 참고하세요)
        return "faq/main";
    }

    @GetMapping("/qna/write")
    public String qnaWriteForm() {
        return "faq/write";
    }

    @GetMapping("/list")
    public String getFaqList(Model model) {
        List<FaqDTO.FaqItem> faqList = new ArrayList<>();

        // 데이터 추가
        faqList.add(new FaqDTO.FaqItem("비밀번호를 잊어버렸어요.", "로그인 화면의 [비밀번호 찾기]에서 아이디와 이메일을 입력하시면 임시 비밀번호 또는 재설정 링크를 이메일로 보내드립니다."));
        faqList.add(new FaqDTO.FaqItem("회원가입은 어떻게 하나요?", "홈페이지 상단 [회원가입] 클릭 후 필수 정보 입력과 이메일 인증을 완료하시면 즉시 이용 가능합니다."));
        faqList.add(new FaqDTO.FaqItem("동영상이 재생되지 않아요.", "최신 Chrome/Edge 사용, 네트워크 확인, 캐시 삭제 후 재시도해 주세요. 해결되지 않으면 [1:1 문의]로 오류 화면을 캡처해 보내주세요."));
        faqList.add(new FaqDTO.FaqItem("아이디를 잊어버렸어요. ", "로그인 화면의 [아이디 찾기]에서 이름과 휴대폰 번호 또는 이메일 인증을 완료하시면 확인하실 수 있습니다."));
        faqList.add(new FaqDTO.FaqItem("결제는 어떻게 하나요? ", "강좌 상세 페이지에서 [수강 신청하기] 클릭 후 원하는 결제 수단을 선택해 절차를 완료하시면 즉시 수강 가능합니다."));

        // 모델에 담기
        model.addAttribute("faqList", faqList);

        return "faq/list"; // faq.html 파일로 이동
    }

}