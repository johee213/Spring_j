package com.example.masil.dto;

import lombok.Getter;


@Getter
public class FaqDTO {

    // 'static' 뒤에 'class'를 꼭 써주셔야 합니다!
    public static class FaqItem {
        private String question;
        private String answer;

        // 생성자 이름에서도 FaqDTO. 부분을 빼고 클래스 이름만 쓰시면 됩니다.
        public FaqItem(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        // @Getter가 FaqDTO 위에만 붙어있다면,
        // 개별 클래스인 FaqItem 위에도 붙여주거나 아래처럼 직접 작성합니다.
        public String getQuestion() { return question; }
        public String getAnswer() { return answer; }
    }
}