package com.meta12.yuni01.repository;

import com.meta12.yuni01.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
