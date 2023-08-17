package com.snuh.smile.repository;

import com.snuh.smile.domain.Auth;
import com.snuh.smile.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Nullable
    Member findByUserId(String userId);
}
