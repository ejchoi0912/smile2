package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Sleeps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SleepRepository extends JpaRepository<Sleeps.Sleep, Integer> {
    @Nullable
    List<Sleeps.Sleep> findByUserAccessToken(String token);

    @Nullable
    Page<Sleeps.Sleep> findByUserAccessTokenContaining(String token, Pageable pageable);
}
