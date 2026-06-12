package com.example.masil.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${gemini.model}") // 프로퍼티에 설정한 모델명을 가져옵니다.
    private String modelName;

    public String askToAI(String question) {
        // 1. URL 구성: 변수로 선언된 apiKey를 사용하여 보안을 강화합니다.
        // gemini-3.1-flash-lite-preview 모델명을 사용하신다면 아래 형식을 유지하세요.
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite:generateContent?key=" + apiKey;
        System.out.println("키 확인: " + apiKey);

        RestTemplate restTemplate = new RestTemplate();

        // 2. 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3. 바디 설정
        String modifiedPrompt = question + " (너는 어르신을 돕는 마실봇이야. " +
                "친절하게 대답하되, 어르신이 읽기 편하게 문장마다 줄바꿈(엔터)을 꼭 넣어줘. " +
                "설명은 핵심만 아주 짧게 하고, 번호 매긴 목록만 확실히 보여드려.)";

        Map<String, Object> body = new HashMap<>();
        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", modifiedPrompt);

        Map<String, Object> partContainer = new HashMap<>();
        partContainer.put("parts", List.of(textPart));
        body.put("contents", List.of(partContainer));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            // 4. API 호출
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map responseBody = response.getBody();

            // 5. 데이터 추출 (오타 수정 포인트)
            List candidates = (List) responseBody.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map firstCandidate = (Map) candidates.get(0);

                // [수정 완료] "templates/content" -> "content"
                Map content = (Map) firstCandidate.get("content");

                List resParts = (List) content.get("parts");
                Map firstPart = (Map) resParts.get(0);
                return firstPart.get("text").toString();
            }
            return "답변을 생성하지 못했습니다.";

        } catch (Exception e) {
            e.printStackTrace();
            return "최종 연결 실패 사유: " + e.getMessage();
        }
    }
}