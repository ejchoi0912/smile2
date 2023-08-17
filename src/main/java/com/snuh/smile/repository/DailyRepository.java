package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Dailies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<Dailies.Daily, Integer> {
    @Nullable
    List<Dailies.Daily> findByUserAccessToken(String token);

    @Nullable
    Page<Dailies.Daily> findByUserAccessTokenContaining(String token, Pageable pageable);
}
