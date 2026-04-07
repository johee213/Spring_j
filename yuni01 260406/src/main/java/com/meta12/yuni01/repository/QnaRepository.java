package com.meta12.yuni01.repository;

import com.meta12.yuni01.entity.GuestBook;
import com.meta12.yuni01.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {

}
