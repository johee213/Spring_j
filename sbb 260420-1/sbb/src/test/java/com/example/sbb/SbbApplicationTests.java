package com.example.sbb;

import com.example.sbb.entity.Question;
import com.example.sbb.repository.QuestionRepository;
import com.example.sbb.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionService questionService;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("300개의 데이터 입력")
	void question_insertManyData() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";

			Question question = new Question();
			question.setSubject(subject);
			question.setContent(content);

			questionRepository.save(question);
		}
	}

	@Test
	void question_insert() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	void question_list() {
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size()); // assertEquals(기대값, 실제값)
	}

	@Test
	void question_one() {
		Optional<Question> oq = this.questionRepository.findById(1L);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
			System.out.println("-- 조회됨 --");
		} else {
			System.out.println("-- 존재하지 않음 --");
		}
	}

	@Test
	void question_findBySubject() {
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	@Test
	void question_findBySubjectAndContent() {
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	@Test
	void question_findBySubjectLike() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}





}
