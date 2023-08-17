package com.snuh.smile.repository;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.UserMetrics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMetricsRepository extends JpaRepository<UserMetrics.UserMetric, Integer> {
    @Nullable
    List<UserMetrics.UserMetric> findByUserAccessToken(String token);

    @Nullable
    Page<UserMetrics.UserMetric> findByUserAccessTokenContaining(String token, Pageable pageable);
}
