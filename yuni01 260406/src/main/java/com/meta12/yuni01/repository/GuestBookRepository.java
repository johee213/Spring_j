package com.meta12.yuni01.repository;

import com.meta12.yuni01.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {

}
